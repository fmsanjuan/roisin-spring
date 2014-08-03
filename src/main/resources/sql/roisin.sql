-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 03-08-2014 a las 16:18:20
-- Versión del servidor: 5.6.12
-- Versión de PHP: 5.5.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `roisin`
--
CREATE DATABASE IF NOT EXISTS `roisin` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `roisin`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Administrator`
--

CREATE TABLE IF NOT EXISTS `Administrator` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_idt4b4u259p6vs4pyr9lax4eg` (`userAccount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `Administrator`
--

INSERT INTO `Administrator` (`id`, `version`, `email`, `name`, `surname`, `userAccount_id`) VALUES
(5, 0, 'a@admin1.com', 'Administrator 1', 'Surname 1', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `DeletedRow`
--

CREATE TABLE IF NOT EXISTS `DeletedRow` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `number` int(11) DEFAULT NULL,
  `preprocessingForm_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_l901h0g3x3888s2ewxm62w0yd` (`preprocessingForm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `File`
--

CREATE TABLE IF NOT EXISTS `File` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `hash` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `originalFile` longblob,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hpo4utwhhxcalm7ml3jwq0iob` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hibernate_sequences`
--

CREATE TABLE IF NOT EXISTS `hibernate_sequences` (
  `sequence_name` varchar(255) DEFAULT NULL,
  `sequence_next_hi_value` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `hibernate_sequences`
--

INSERT INTO `hibernate_sequences` (`sequence_name`, `sequence_next_hi_value`) VALUES
('DomainEntity', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PreprocessedData`
--

CREATE TABLE IF NOT EXISTS `PreprocessedData` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `exampleSet` longblob,
  `name` varchar(255) DEFAULT NULL,
  `preprocessingForm_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_dea1kimbjlto9voehdf0bvsj2` (`preprocessingForm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `PreprocessingForm`
--

CREATE TABLE IF NOT EXISTS `PreprocessingForm` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `filterCondition` varchar(255) DEFAULT NULL,
  `file_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_orh9ovajd06hhh296gmco1t48` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Process`
--

CREATE TABLE IF NOT EXISTS `Process` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `algorithm` varchar(255) DEFAULT NULL,
  `label_id` int(11) DEFAULT NULL,
  `preprocessedData_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ptcp0r2oejrt2nk0t8pu7lj1u` (`label_id`),
  KEY `FK_ju0bhbyfw9cdkywnyogbfio4h` (`preprocessedData_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Results`
--

CREATE TABLE IF NOT EXISTS `Results` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `auc` double DEFAULT NULL,
  `process_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_s3u7cy0btaf0v0v912c1vvv5t` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `RipperSettings`
--

CREATE TABLE IF NOT EXISTS `RipperSettings` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `minimalPruneBenefit` double DEFAULT NULL,
  `pureness` double DEFAULT NULL,
  `ripperCriterion` varchar(255) DEFAULT NULL,
  `sampleRatio` double DEFAULT NULL,
  `process_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_p772o6rye7776qnwbstttboan` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Rule`
--

CREATE TABLE IF NOT EXISTS `Rule` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `auc` double DEFAULT NULL,
  `conclusion` varchar(255) DEFAULT NULL,
  `fn` int(11) DEFAULT NULL,
  `fp` int(11) DEFAULT NULL,
  `fpr` double DEFAULT NULL,
  `premise` varchar(255) DEFAULT NULL,
  `rulePrecision` double DEFAULT NULL,
  `support` double DEFAULT NULL,
  `tn` int(11) DEFAULT NULL,
  `tp` int(11) DEFAULT NULL,
  `tpr` double DEFAULT NULL,
  `results_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_bfrqr92ht500ryauufrfvviyj` (`results_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `SelectedAttribute`
--

CREATE TABLE IF NOT EXISTS `SelectedAttribute` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `preprocessingForm_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_qo6jqtuogeou696ddcod3971l` (`preprocessingForm_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `SubgroupSettings`
--

CREATE TABLE IF NOT EXISTS `SubgroupSettings` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `kBestRules` int(11) DEFAULT NULL,
  `maxDepth` int(11) DEFAULT NULL,
  `minCoverage` double DEFAULT NULL,
  `minUtility` double DEFAULT NULL,
  `mode` varchar(255) DEFAULT NULL,
  `ruleGeneration` varchar(255) DEFAULT NULL,
  `utilityFunction` varchar(255) DEFAULT NULL,
  `process_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_m1g4f7hs0id1q2ikkxhp5ex7i` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `TreeToRulesSettings`
--

CREATE TABLE IF NOT EXISTS `TreeToRulesSettings` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `confidence` double DEFAULT NULL,
  `maximalDepth` int(11) DEFAULT NULL,
  `minimalGain` double DEFAULT NULL,
  `minimalLeafSize` int(11) DEFAULT NULL,
  `minimalSizeForSplit` int(11) DEFAULT NULL,
  `noPrepruning` bit(1) DEFAULT NULL,
  `noPruning` bit(1) DEFAULT NULL,
  `numberOfPrepruningAlternatives` int(11) DEFAULT NULL,
  `tree2RulesCriterion` varchar(255) DEFAULT NULL,
  `process_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3ex118p2iqiu5digxtjn92wpl` (`process_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `userAccount_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o6s94d43co03sx067ili5760c` (`userAccount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `User`
--

INSERT INTO `User` (`id`, `version`, `city`, `email`, `name`, `nationality`, `surname`, `userAccount_id`) VALUES
(6, 0, 'Seville', 'c@customer1.com', 'Customer 1', 'Spain', 'SurnameC 1', 2),
(7, 0, 'Seville', 'c@customer1.com', 'Customer 1', 'Spain', 'SurnameC 1', 3),
(8, 0, 'Seville', 'c@customer2.com', 'Customer 2', 'Spain', 'SurnameC 2', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `UserAccount`
--

CREATE TABLE IF NOT EXISTS `UserAccount` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_csivo9yqa08nrbkog71ycilh5` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `UserAccount`
--

INSERT INTO `UserAccount` (`id`, `version`, `password`, `username`) VALUES
(1, 0, '21232f297a57a5a743894a0e4a801fc3', 'admin'),
(2, 0, 'ffbc4675f864e0e9aab8bdf7a0437010', 'customer1'),
(3, 0, '25779f8829ab7a7650e85a4cc871e6ac', 'felix'),
(4, 0, '5ce4d191fd14ac85a1469fb8c29b7a7b', 'customer2');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `UserAccount_authorities`
--

CREATE TABLE IF NOT EXISTS `UserAccount_authorities` (
  `UserAccount_id` int(11) NOT NULL,
  `authority` varchar(255) DEFAULT NULL,
  KEY `FK_b63ua47r0u1m7ccc9lte2ui4r` (`UserAccount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `UserAccount_authorities`
--

INSERT INTO `UserAccount_authorities` (`UserAccount_id`, `authority`) VALUES
(1, 'ADMIN'),
(2, 'USER'),
(3, 'USER'),
(4, 'USER');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Administrator`
--
ALTER TABLE `Administrator`
  ADD CONSTRAINT `FK_idt4b4u259p6vs4pyr9lax4eg` FOREIGN KEY (`userAccount_id`) REFERENCES `UserAccount` (`id`);

--
-- Filtros para la tabla `DeletedRow`
--
ALTER TABLE `DeletedRow`
  ADD CONSTRAINT `FK_l901h0g3x3888s2ewxm62w0yd` FOREIGN KEY (`preprocessingForm_id`) REFERENCES `PreprocessingForm` (`id`);

--
-- Filtros para la tabla `File`
--
ALTER TABLE `File`
  ADD CONSTRAINT `FK_hpo4utwhhxcalm7ml3jwq0iob` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

--
-- Filtros para la tabla `PreprocessedData`
--
ALTER TABLE `PreprocessedData`
  ADD CONSTRAINT `FK_dea1kimbjlto9voehdf0bvsj2` FOREIGN KEY (`preprocessingForm_id`) REFERENCES `PreprocessingForm` (`id`);

--
-- Filtros para la tabla `PreprocessingForm`
--
ALTER TABLE `PreprocessingForm`
  ADD CONSTRAINT `FK_orh9ovajd06hhh296gmco1t48` FOREIGN KEY (`file_id`) REFERENCES `File` (`id`);

--
-- Filtros para la tabla `Process`
--
ALTER TABLE `Process`
  ADD CONSTRAINT `FK_ju0bhbyfw9cdkywnyogbfio4h` FOREIGN KEY (`preprocessedData_id`) REFERENCES `PreprocessedData` (`id`),
  ADD CONSTRAINT `FK_ptcp0r2oejrt2nk0t8pu7lj1u` FOREIGN KEY (`label_id`) REFERENCES `SelectedAttribute` (`id`);

--
-- Filtros para la tabla `Results`
--
ALTER TABLE `Results`
  ADD CONSTRAINT `FK_s3u7cy0btaf0v0v912c1vvv5t` FOREIGN KEY (`process_id`) REFERENCES `Process` (`id`);

--
-- Filtros para la tabla `RipperSettings`
--
ALTER TABLE `RipperSettings`
  ADD CONSTRAINT `FK_p772o6rye7776qnwbstttboan` FOREIGN KEY (`process_id`) REFERENCES `Process` (`id`);

--
-- Filtros para la tabla `Rule`
--
ALTER TABLE `Rule`
  ADD CONSTRAINT `FK_bfrqr92ht500ryauufrfvviyj` FOREIGN KEY (`results_id`) REFERENCES `Results` (`id`);

--
-- Filtros para la tabla `SelectedAttribute`
--
ALTER TABLE `SelectedAttribute`
  ADD CONSTRAINT `FK_qo6jqtuogeou696ddcod3971l` FOREIGN KEY (`preprocessingForm_id`) REFERENCES `PreprocessingForm` (`id`);

--
-- Filtros para la tabla `SubgroupSettings`
--
ALTER TABLE `SubgroupSettings`
  ADD CONSTRAINT `FK_m1g4f7hs0id1q2ikkxhp5ex7i` FOREIGN KEY (`process_id`) REFERENCES `Process` (`id`);

--
-- Filtros para la tabla `TreeToRulesSettings`
--
ALTER TABLE `TreeToRulesSettings`
  ADD CONSTRAINT `FK_3ex118p2iqiu5digxtjn92wpl` FOREIGN KEY (`process_id`) REFERENCES `Process` (`id`);

--
-- Filtros para la tabla `User`
--
ALTER TABLE `User`
  ADD CONSTRAINT `FK_o6s94d43co03sx067ili5760c` FOREIGN KEY (`userAccount_id`) REFERENCES `UserAccount` (`id`);

--
-- Filtros para la tabla `UserAccount_authorities`
--
ALTER TABLE `UserAccount_authorities`
  ADD CONSTRAINT `FK_b63ua47r0u1m7ccc9lte2ui4r` FOREIGN KEY (`UserAccount_id`) REFERENCES `UserAccount` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
