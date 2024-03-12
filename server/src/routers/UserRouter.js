import express from "express";
import {
  login,
  register,
  getCurrentUser,
} from "../controllers/UserController.js";

const router = express.Router();
router.post("/login", login);
router.post("/register", register);
router.get("/current-user", getCurrentUser);

export default router;
