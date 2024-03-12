import express from "express";
import { getTextFromImage } from "../controllers/ImageController.js";
import { upload } from "../../multer.js";

const router = express.Router();
router.post("/images-to-text", upload.single("file"), getTextFromImage);

export default router;
