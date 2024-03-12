import Sequelize from "sequelize";
import db from "../util/database.js";

const User = db.define("users", {
  id: {
    type: Sequelize.INTEGER,
    autoIncrement: true,
    allowNull: false,
    primaryKey: true,
  },
  username: Sequelize.TEXT,
  email: {
    type: Sequelize.TEXT,
  },
  password: {
    allowNull: false,
    type: Sequelize.TEXT,
  },
});

export default User;
