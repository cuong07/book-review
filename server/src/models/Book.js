import Sequelize from "sequelize";
import db from "../util/database.js";

const Book = db.define("books", {
  id: {
    type: Sequelize.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true,
  },
  image: Sequelize.TEXT,
  name: {
    type: Sequelize.TEXT,
  },
  author: {
    allowNull: false,
    type: Sequelize.TEXT,
  },
});

export default Book;
