import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

import { generateRefreshToken, generateToken } from "../util/JwtTokenUtils.js";
import User from "../models/User.js";
import dotenv from "dotenv";
dotenv.config();

let refreshTokens = [];

const register = async (req, res) => {
  try {
    const { username, email, password, retypePassword } = req.body;
    const exist = await User.findOne({
      where: {
        username,
      },
    });
    if (exist) {
      return res.status(400).json({
        message: `username đã tồn tại vui lòng sử dụng một username khác`,
      });
    }
    if (password !== retypePassword) {
      return res.status(400).json({
        message: `Mật khẩu không khớp`,
      });
    }
    const hashedPassword = await bcrypt.hash(password, 8);
    const user = await User.create({
      username,
      email,
      password: hashedPassword,
    });
    return res.json({
      user,
      message: "Thành công",
    });
  } catch (error) {
    console.log(error);
    return res.status(400).json({ message: error.message });
  }
};

const login = async (req, res) => {
  try {
    const { username, password } = req.body;
    const exist = await User.findOne({
      where: {
        username,
      },
    });
    if (!exist) {
      return res
        .status(400)
        .json({ message: `Tài khoản không tồn tại hoặc mật khẩu không khớp` });
    }
    const isMatch = await bcrypt.compare(password, exist.password);
    if (!isMatch) {
      return res
        .status(400)
        .json({ message: "Tài khoản không tồn tại hoặc mật khẩu không khớp" });
    }
    const token = generateToken(exist);
    const refreshToken = generateRefreshToken(exist);
    refreshTokens.push(refreshToken);
    // res.cookie("redreshToken", refreshToken, {
    //   httpOnly: true,
    //   scure: true,
    //   path: "/",
    //   sameSite: "strict",
    // });
    return res.status(200).json({
      user: exist,
      token,
      message: "Thành công",
    });
  } catch (error) {
    console.log(error);
    return res.status(400).json({ error });
  }
};

const getCurrentUser = async (req, res) => {
  console.log(req.headers);
  try {
    const { authorization } = req.headers;
    console.log(req.headers);
    let existUserId;
    if (authorization) {
      const accessToken = authorization.split(" ")[1];
      jwt.verify(accessToken, process.env.SECRET_KEY, (err, user) => {
        if (err) {
          if (err.name === "TokenExpiredError") {
            return res.status(401).json({ message: "Token has expired" });
          }
          return res.status(401).json({ message: "Invalid token" });
        }
        existUserId = user.id;
      });
      const existUser = await User.findByPk(existUserId);
      return res.status(200).json({
        user: existUser,
        message: "Thành công",
      });
    } else {
      return res.status(401).json({ message: "No token provided" });
    }
  } catch (error) {
    console.log(error);
    return res.status(400).json({ error });
  }
};
export { register, login, getCurrentUser };
