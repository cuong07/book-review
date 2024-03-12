import Sequelize from "sequelize";
import db from "../util/database.js";

const Wishlist = db.define("wishlists", {
  id: {
    type: Sequelize.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true,
  },
  name: Sequelize.TEXT,
  note: Sequelize.TEXT,
});

export default Wishlist;
