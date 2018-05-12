-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

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
  `role` ENUM('ADMIN', 'USER', 'DRIVER') NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `login` ON `trucking`.`app_user` (`login` ASC);


-- -----------------------------------------------------
-- Table `trucking`.`cities`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`cities` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `city_name` VARCHAR(100) NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  PRIMARY KEY (`id`, `city_name`))
ENGINE = InnoDB
AUTO_INCREMENT = 5
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `city_name_UNIQUE` ON `trucking`.`cities` (`city_name` ASC);

CREATE UNIQUE INDEX `id_UNIQUE` ON `trucking`.`cities` (`id` ASC);


-- -----------------------------------------------------
-- Table `trucking`.`trucks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`trucks` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `reg_number` VARCHAR(7) NOT NULL,
  `shift_period` INT(11) NOT NULL,
  `capacity_ton` INT(11) NOT NULL,
  `truck_condition` ENUM('OK', 'Faulty') NOT NULL,
  `city_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `Truck_city`
    FOREIGN KEY (`city_id`)
    REFERENCES `trucking`.`cities` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 24
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `reg_number_UNIQUE` ON `trucking`.`trucks` (`reg_number` ASC);

CREATE UNIQUE INDEX `id_UNIQUE` ON `trucking`.`trucks` (`id` ASC);

CREATE INDEX `Truck_city_idx` ON `trucking`.`trucks` (`city_id` ASC);


-- -----------------------------------------------------
-- Table `trucking`.`orders`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`orders` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `order_status` ENUM('Created', 'In_Process', 'Done') NOT NULL,
  `truck_id` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `Order_Truck`
    FOREIGN KEY (`truck_id`)
    REFERENCES `trucking`.`trucks` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id_UNIQUE` ON `trucking`.`orders` (`id` ASC);

CREATE INDEX `Order_Truck_idx` ON `trucking`.`orders` (`truck_id` ASC);


-- -----------------------------------------------------
-- Table `trucking`.`cargoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`cargoes` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `cargo_name` VARCHAR(45) NOT NULL,
  `weight_kg` INT(11) NOT NULL,
  `delivery_status` ENUM('PREPARED', 'SHIPPED', 'DELIVERED') NOT NULL,
  `order_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `Cargo_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `trucking`.`orders` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id_UNIQUE` ON `trucking`.`cargoes` (`id` ASC);

CREATE INDEX `Cargo_order_idx` ON `trucking`.`cargoes` (`order_id` ASC);


-- -----------------------------------------------------
-- Table `trucking`.`drivers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`drivers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `driver_name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `worked_this_month` INT(11) NOT NULL,
  `phase_of_work` ENUM('Driving', 'Second_Driver', 'Loading', 'Rest') NOT NULL,
  `current_city` VARCHAR(45) NULL DEFAULT NULL,
  `truck_id` INT(11) NULL DEFAULT NULL,
  CONSTRAINT `Driver_Truck`
    FOREIGN KEY (`truck_id`)
    REFERENCES `trucking`.`trucks` (`id`),
  CONSTRAINT `Driver_city`
    FOREIGN KEY (`current_city`)
    REFERENCES `trucking`.`cities` (`city_name`),
  CONSTRAINT `drivers_app_user_fk`
    FOREIGN KEY (`id`)
    REFERENCES `trucking`.`app_user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id_UNIQUE` ON `trucking`.`drivers` (`id` ASC);

CREATE INDEX `City_idx` ON `trucking`.`drivers` (`current_city` ASC);

CREATE INDEX `Driver_Truck_idx` ON `trucking`.`drivers` (`truck_id` ASC);


-- -----------------------------------------------------
-- Table `trucking`.`order_history`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`order_history` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `driver_id` INT(11) NOT NULL,
  `order_id` INT(11) NOT NULL,
  `shift_begined` DATETIME NOT NULL,
  `shift_ended` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `History_driver`
    FOREIGN KEY (`driver_id`)
    REFERENCES `trucking`.`drivers` (`id`),
  CONSTRAINT `History_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `trucking`.`orders` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id_UNIQUE` ON `trucking`.`order_history` (`id` ASC);

CREATE INDEX `History_driver_idx` ON `trucking`.`order_history` (`driver_id` ASC);

CREATE INDEX `History_order_idx` ON `trucking`.`order_history` (`order_id` ASC);


-- -----------------------------------------------------
-- Table `trucking`.`waypoints`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `trucking`.`waypoints` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `city` VARCHAR(45) NOT NULL,
  `order_id` INT(11) NOT NULL,
  `waypoint_operation` ENUM('LOADING', 'UNLOADING') NOT NULL,
  `cargo_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `Waypoint_cargo`
    FOREIGN KEY (`cargo_id`)
    REFERENCES `trucking`.`cargoes` (`id`),
  CONSTRAINT `Waypoint_city`
    FOREIGN KEY (`city`)
    REFERENCES `trucking`.`cities` (`city_name`),
  CONSTRAINT `Waypoint_order`
    FOREIGN KEY (`order_id`)
    REFERENCES `trucking`.`orders` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;

CREATE UNIQUE INDEX `id_UNIQUE` ON `trucking`.`waypoints` (`id` ASC);

CREATE INDEX `Waypoint_city_idx` ON `trucking`.`waypoints` (`city` ASC);

CREATE INDEX `Waypoint_order_idx` ON `trucking`.`waypoints` (`order_id` ASC);

CREATE INDEX `Waypoint_cargo_idx` ON `trucking`.`waypoints` (`cargo_id` ASC);

INSERT INTO app_user (login, password, role) VALUES
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
