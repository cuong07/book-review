import Sequelize from "sequelize";
import db from "../util/database.js";

const WishlistItem = db.define("wishlist_items", {
  id: {
    type: Sequelize.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true,
  },
});

export default WishlistItem;
