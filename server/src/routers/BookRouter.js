import express from "express";
import {
  getReviewFromImage,
  getReviewFromText,
  getAllBooks,
} from "../controllers/BookController.js";
import { upload } from "../../multer.js";

const router = express.Router();
router.post("/book/images-to-text", upload.single("file"), getReviewFromImage);
router.get("/book", getReviewFromText);
router.get("/book/user", getAllBooks);

export default router;
