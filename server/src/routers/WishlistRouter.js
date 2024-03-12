import express from "express";
import {
  addToWishlist,
  getWishListByUser,
  removeWishlistItem,
} from "../controllers/WishlistController.js";

const router = express.Router();
router.post("/wishlist", addToWishlist);
router.get("/wishlist", getWishListByUser);
router.delete("/wishlist", removeWishlistItem);

export default router;
