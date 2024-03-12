import jwt from "jsonwebtoken";
import dotenv from "dotenv";
dotenv.config();

const generateToken = (user) => {
  return jwt.sign(
    {
      email: user.email,
      id: user.id,
      username: user.username,
    },
    process.env.SECRET_KEY,
    { expiresIn: "1d" }
  );
};

const generateRefreshToken = (user) => {
  return jwt.sign(
    {
      email: user.email,
      id: user.id,
      username: user.username,
    },
    process.env.SECRET_REFRESHTOKEN_KEY,
    { expiresIn: "365d" }
  );
};
export { generateRefreshToken, generateToken };
