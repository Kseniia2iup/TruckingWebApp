-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema trucking
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema trucking
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `trucking` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `trucking` ;

-- -----------------------------------------------------
-- Table `trucking`.`app_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`app_user` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(30) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `user_role` ENUM('ADMIN', 'USER', 'DRIVER') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login` (`login` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 38
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trucking`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`cities` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `city_name` VARCHAR(100) NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  PRIMARY KEY (`id`, `city_name`),
  UNIQUE INDEX `city_name_UNIQUE` (`city_name` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trucking`.`trucks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`trucks` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `reg_number` VARCHAR(7) NOT NULL,
  `shift_period` INT(11) NOT NULL,
  `capacity_ton` INT(11) NOT NULL,
  `truck_condition` ENUM('OK', 'FAULTY') NOT NULL,
  `city_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `reg_number_UNIQUE` (`reg_number` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `Truck_city_idx` (`city_id` ASC),
  CONSTRAINT `Truck_city`
    FOREIGN KEY (`city_id`)
    REFERENCES `trucking`.`cities` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 33
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trucking`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`orders` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `order_status` ENUM('CREATED', 'IN_PROCESS', 'INTERRUPTED', 'DONE') NOT NULL,
  `truck_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `Order_Truck_idx` (`truck_id` ASC),
  CONSTRAINT `Order_Truck`
    FOREIGN KEY (`truck_id`)
    REFERENCES `trucking`.`trucks` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 25
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trucking`.`cargoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`cargoes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cargo_name` VARCHAR(45) NOT NULL,
  `weight_kg` INT(11) NOT NULL,
  `delivery_status` ENUM('PREPARED', 'SHIPPED', 'DELIVERED') NOT NULL,
  `order_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `Cargo_order_idx` (`order_id` ASC),
  CONSTRAINT `Cargo_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `trucking`.`orders` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trucking`.`drivers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`drivers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `driver_name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `worked_this_month` INT(11) NOT NULL,
  `phase_of_work` ENUM('DRIVING', 'SECOND_DRIVER', 'LOADING', 'REST') NOT NULL,
  `truck_id` INT(11) NULL DEFAULT NULL,
  `city_id` INT(11) NULL DEFAULT NULL,
  `order_id` INT(11) NULL DEFAULT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `Driver_Truck_idx` (`truck_id` ASC),
  INDEX `drivers_city__fk` (`city_id` ASC),
  INDEX `drivers_order__fk` (`order_id` ASC),
  CONSTRAINT `Driver_Truck`
    FOREIGN KEY (`truck_id`)
    REFERENCES `trucking`.`trucks` (`id`),
  CONSTRAINT `drivers_app_user_fk`
    FOREIGN KEY (`id`)
    REFERENCES `trucking`.`app_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `drivers_city__fk`
    FOREIGN KEY (`city_id`)
    REFERENCES `trucking`.`cities` (`id`),
  CONSTRAINT `drivers_order__fk`
    FOREIGN KEY (`order_id`)
    REFERENCES `trucking`.`orders` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 38
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trucking`.`order_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`order_history` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `driver_id` INT(11) NOT NULL,
  `shift_begined` DATETIME NULL DEFAULT NULL,
  `shift_ended` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `History_driver_idx` (`driver_id` ASC),
  CONSTRAINT `History_driver`
    FOREIGN KEY (`driver_id`)
    REFERENCES `trucking`.`drivers` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `trucking`.`waypoints`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`waypoints` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `order_id` INT(11) NULL DEFAULT NULL,
  `cargo_id` INT(11) NOT NULL,
  `city_dep_id` INT(11) NULL DEFAULT NULL,
  `city_dest_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `Waypoint_order_idx` (`order_id` ASC),
  INDEX `Waypoint_cargo_idx` (`cargo_id` ASC),
  INDEX `waypoints_city__fk` (`city_dep_id` ASC),
  INDEX `waypoints_city_dest_fk_idx` (`city_dest_id` ASC),
  CONSTRAINT `Waypoint_cargo`
    FOREIGN KEY (`cargo_id`)
    REFERENCES `trucking`.`cargoes` (`id`),
  CONSTRAINT `Waypoint_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `trucking`.`orders` (`id`),
  CONSTRAINT `waypoints_city__fk`
    FOREIGN KEY (`city_dep_id`)
    REFERENCES `trucking`.`cities` (`id`),
  CONSTRAINT `waypoints_city_dest_fk`
    FOREIGN KEY (`city_dest_id`)
    REFERENCES `trucking`.`cities` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 15
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

INSERT INTO app_user (login, password, user_role) VALUES
  ('Admin01', '$2a$10$4eqIF5s/ewJwHK1p8lqlFOEm2QIA0S8g6./Lok.pQxqcxaBZYChRm', 'ADMIN');

INSERT INTO cities (city_name, latitude, longitude) VALUES
  ('Saint Petersburg', 59.938806, 30.314278),
  ('Moscow', 55.755773, 37.617761);

INSERT INTO trucks (reg_number, shift_period, capacity_ton, truck_condition, city_id) VALUES
  ('AN29304', 100, 4, 'OK', 1),
  ('AN29344', 140, 4, 'OK', 2),
  ('AN29354', 300, 10, 'OK', 1),
  ('AN29364', 100, 6, 'Faulty', 1),
  ('AN29374', 100, 4, 'OK', 2),
  ('HF39025', 200, 8, 'Faulty', 2);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
