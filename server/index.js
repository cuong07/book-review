import express from "express";
import cors from "cors";
import bodyParser from "body-parser";
import dotenv from "dotenv";
import {
  ImageRouter,
  UserRouter,
  BookRouter,
  WishlistRouter,
} from "./src/routers/index.js";
import { Book, User, Wishlist, WishlistItem } from "./src/models/index.js";
import db from "./src/util/database.js";
dotenv.config();

const app = express();
app.use(express.json());
app.use(bodyParser.json({ limit: "30mb", extended: true }));
app.use(bodyParser.urlencoded({ limit: "30mb", extended: true }));
app.use(cors({ origin: true, credentials: true }));

User.hasMany(Book, { foreignKey: "user_id" });
User.hasMany(Wishlist, { foreignKey: "user_id" });
Wishlist.hasMany(WishlistItem, { foreignKey: "wishlist_id" });
WishlistItem.belongsTo(Book, { foreignKey: "book_id" });

// router
app.use("/api/v1", ImageRouter);
app.use("/api/v1", UserRouter);
app.use("/api/v1", BookRouter);
app.use("/api/v1", WishlistRouter);

app.get((req, res) => {
  res.status(404).send("Sorry, resource not found");
});

const PORT = process.env.PORT || 5001;
// { force: true }
db.sync()
  .then(() => {
    app.listen(PORT, () => {
      console.log(`server running on port: http://localhost:${PORT}`);
    });
  })
  .catch((err) => {
    console.log(err);
  });
