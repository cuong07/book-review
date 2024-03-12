import WishlistItem from "../models/WishlistItem.js";
import Wishlist from "../models/Wishlist.js";
import dotenv from "dotenv";
import Book from "../models/Book.js";
dotenv.config();

const addToWishlist = async (req, res) => {
  const { book_id, user_id } = req.query;
  try {
    const existWishlist = await Wishlist.findOne({
      where: {
        user_id,
      },
    });
    if (!existWishlist) {
      const newWishlist = await Wishlist.create({
        name: "Danh sách yêu thích",
        note: "Sách yêu thích",
        user_id,
      });
      const newItem = await WishlistItem.create({
        book_id,
        wishlist_id: newWishlist.id,
      });

      return res.status(200).json({
        data: newItem,
        message: "Thành công",
      });
    }

    const existItem = await WishlistItem.findOne({
      where: {
        book_id,
        wishlist_id: existWishlist.id,
      },
    });

    if (existItem) {
      await WishlistItem.destroy({
        where: {
          id: existItem.id,
        },
      });
      return res.status(200).json({
        data: {},
        message: "Thành cong",
      });
    }
    const newItem = await WishlistItem.create({
      book_id,
      wishlist_id: existWishlist.id,
    });
    return res.status(200).json({
      data: newItem,
      message: "Thành công",
    });
  } catch (error) {
    console.log(error);
    return res.status(400).json({
      data: {},
      message: "Không thành công",
      error,
    });
  }
};

const getWishListByUser = async (req, res) => {
  const { user_id } = req.query;
  try {
    const wishlist = await Wishlist.findOne({
      where: {
        user_id,
      },
      include: {
        model: WishlistItem,
        include: Book,
      },
    });

    return res.status(200).json({
      data: wishlist,
      message: "Thành công ",
    });
  } catch (error) {
    console.log(error);
    return res.status(400).json({
      data: {},
      message: "Không thành công ",
      error,
    });
  }
};

const removeWishlistItem = async (req, res) => {
  const { wishlist_item_id } = req.query;
  try {
    await WishlistItem.destroy({
      where: {
        id: wishlist_item_id,
      },
    });
    return res.status(200).json({
      data: {},
      message: "Thành công",
    });
  } catch (error) {
    console.log(error);
    return res.status(400).json({
      data: {},
      message: "Khong thành công",
    });
  }
};

export { addToWishlist, getWishListByUser, removeWishlistItem };
