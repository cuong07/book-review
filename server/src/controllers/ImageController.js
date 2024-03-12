import { createWorker } from "tesseract.js";
import { uploads } from "../../cloudinary.js";
import { Book } from "../models/index.js";

const API = "https://api.nytimes.com/svc/books/v3/reviews.json";
// ?title=J. K. Rowling&api-key=uVOEbmH0VwhCKNss6Ocs7u9RuGp3VeJr

const getTextFromImage = async (req, res) => {
  try {
    const { type } = req.query;

    if (req.file) {
      const uploader = async (path) => await uploads(path, "books");
      const bookCover = req.file.path;

      // trích xuất chữ từ hình ảnh
      const worker = await createWorker("eng");
      const text = await worker.recognize(bookCover);
      await worker.terminate();

      const textData = text.data.text.replace(/\n/g, " ");
      const url = `${API}?${type}=${textData}&api-key=${process.env.API_KEY}`;
      const response = await fetch(url, {
        method: "GET",
      });

      const listReview = await response.json();

      const existBook = await Book.findOne({
        where: {
          name: textData,
        },
      });
      if (!existBook) {
        const newPath = await uploader(bookCover);

        const newBook = await Book.create({
          name: textData,
          image: newPath.url,
          author: textData,
          user_id: 1,
        });
        return res.status(200).json({
          book: newBook,
          data: listReview,
        });
      }
      return res.status(200).json({
        book: existBook,
        data: listReview,
      });
    }
    return res.status(400).json({
      data: [],
      message: "Không có file",
    });
  } catch (error) {
    console.log("Error: " + error);
    return res.status(500).json({ error: "Internal Server Error" });
  }
};

export { getTextFromImage };
