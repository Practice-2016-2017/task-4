-- MySQL Script generated by MySQL Workbench
-- 04/17/17 02:36:54
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema StRB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema StRB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `StRB` DEFAULT CHARACTER SET utf8 ;
USE `StRB` ;

-- -----------------------------------------------------
-- Table `StRB`.`Year`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StRB`.`Year` (
  `idYears` INT NOT NULL AUTO_INCREMENT,
  `Year` INT NOT NULL,
  PRIMARY KEY (`idYears`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StRB`.`Student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StRB`.`Student` (
  `idStudent` INT NOT NULL AUTO_INCREMENT,
  `StudentName` VARCHAR(120) NOT NULL,
  `Login` INT NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  `Years_idYears` INT NOT NULL,
  PRIMARY KEY (`idStudent`),
  INDEX `fk_Student_Years1_idx` (`Years_idYears` ASC),
  UNIQUE INDEX `Login_UNIQUE` (`Login` ASC),
  CONSTRAINT `fk_Student_Years1`
    FOREIGN KEY (`Years_idYears`)
    REFERENCES `StRB`.`Year` (`idYears`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StRB`.`Teacher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StRB`.`Teacher` (
  `idTeacher` INT NOT NULL AUTO_INCREMENT,
  `TeacherName` VARCHAR(120) NOT NULL,
  `Login` INT NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idTeacher`),
  UNIQUE INDEX `Login_UNIQUE` (`Login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StRB`.`Admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StRB`.`Admin` (
  `idAdmin` INT NOT NULL AUTO_INCREMENT,
  `Login` INT NOT NULL,
  `Password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idAdmin`),
  UNIQUE INDEX `Login_UNIQUE` (`Login` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StRB`.`Subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StRB`.`Subject` (
  `idSubject` INT NOT NULL AUTO_INCREMENT,
  `SubjectName` VARCHAR(45) NOT NULL,
  `Years_idYears` INT NOT NULL,
  `Teacher_idTeacher` INT NOT NULL,
  PRIMARY KEY (`idSubject`),
  INDEX `fk_Subject_Years1_idx` (`Years_idYears` ASC),
  INDEX `fk_Subject_Teacher1_idx` (`Teacher_idTeacher` ASC),
  CONSTRAINT `fk_Subject_Years1`
    FOREIGN KEY (`Years_idYears`)
    REFERENCES `StRB`.`Year` (`idYears`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Subject_Teacher1`
    FOREIGN KEY (`Teacher_idTeacher`)
    REFERENCES `StRB`.`Teacher` (`idTeacher`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `StRB`.`Mark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `StRB`.`Mark` (
  `idMark` INT NOT NULL AUTO_INCREMENT,
  `Mark` INT NOT NULL,
  `Date` DATE NOT NULL,
  `Subject_idSubject` INT NOT NULL,
  `Student_idStudent` INT NOT NULL,
  INDEX `fk_Mark_Subject1_idx` (`Subject_idSubject` ASC),
  INDEX `fk_Mark_Student1_idx` (`Student_idStudent` ASC),
  PRIMARY KEY (`idMark`),
  CONSTRAINT `fk_Mark_Subject1`
    FOREIGN KEY (`Subject_idSubject`)
    REFERENCES `StRB`.`Subject` (`idSubject`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mark_Student1`
    FOREIGN KEY (`Student_idStudent`)
    REFERENCES `StRB`.`Student` (`idStudent`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `StRB`.`Year`
-- -----------------------------------------------------
START TRANSACTION;
USE `StRB`;
INSERT INTO `StRB`.`Year` (`idYears`, `Year`) VALUES (1, 1);
INSERT INTO `StRB`.`Year` (`idYears`, `Year`) VALUES (2, 2);
INSERT INTO `StRB`.`Year` (`idYears`, `Year`) VALUES (3, 3);
INSERT INTO `StRB`.`Year` (`idYears`, `Year`) VALUES (4, 4);

COMMIT;


-- -----------------------------------------------------
-- Data for table `StRB`.`Student`
-- -----------------------------------------------------
START TRANSACTION;
USE `StRB`;
INSERT INTO `StRB`.`Student` (`idStudent`, `StudentName`, `Login`, `Password`, `Years_idYears`) VALUES (1, 'Belonogov Bogdan', 100001, 'belobo', 1);
INSERT INTO `StRB`.`Student` (`idStudent`, `StudentName`, `Login`, `Password`, `Years_idYears`) VALUES (2, 'Shestakova Irina', 100002, 'sheir', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `StRB`.`Teacher`
-- -----------------------------------------------------
START TRANSACTION;
USE `StRB`;
INSERT INTO `StRB`.`Teacher` (`idTeacher`, `TeacherName`, `Login`, `Password`) VALUES (1, 'Victor Zherbin', 100001, 'vizhe');
INSERT INTO `StRB`.`Teacher` (`idTeacher`, `TeacherName`, `Login`, `Password`) VALUES (2, 'Semen Pavlov', 100002, 'sepa');

COMMIT;


-- -----------------------------------------------------
-- Data for table `StRB`.`Admin`
-- -----------------------------------------------------
START TRANSACTION;
USE `StRB`;
INSERT INTO `StRB`.`Admin` (`idAdmin`, `Login`, `Password`) VALUES (1, 100000, 'root');

COMMIT;


-- -----------------------------------------------------
-- Data for table `StRB`.`Subject`
-- -----------------------------------------------------
START TRANSACTION;
USE `StRB`;
INSERT INTO `StRB`.`Subject` (`idSubject`, `SubjectName`, `Years_idYears`, `Teacher_idTeacher`) VALUES (1, 'Algebra', 1, 1);
INSERT INTO `StRB`.`Subject` (`idSubject`, `SubjectName`, `Years_idYears`, `Teacher_idTeacher`) VALUES (2, 'Geometry', 1, 2);

COMMIT;


-- -----------------------------------------------------
-- Data for table `StRB`.`Mark`
-- -----------------------------------------------------
START TRANSACTION;
USE `StRB`;
INSERT INTO `StRB`.`Mark` (`idMark`, `Mark`, `Date`, `Subject_idSubject`, `Student_idStudent`) VALUES (1, 3, '2017-01-10', 1, 1);
INSERT INTO `StRB`.`Mark` (`idMark`, `Mark`, `Date`, `Subject_idSubject`, `Student_idStudent`) VALUES (2, 4, '2017-01-10', 1, 2);
INSERT INTO `StRB`.`Mark` (`idMark`, `Mark`, `Date`, `Subject_idSubject`, `Student_idStudent`) VALUES (3, 4, '2017-01-14 ', 2, 1);
INSERT INTO `StRB`.`Mark` (`idMark`, `Mark`, `Date`, `Subject_idSubject`, `Student_idStudent`) VALUES (4, 5, '2017-01-14', 2, 2);

COMMIT;

