-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 22-07-2014 a las 01:36:45
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Examples`
--

CREATE TABLE IF NOT EXISTS `Examples` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `exampleSet` longblob,
  `filePath` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cqtkdv36vuws0k5w57c967k6r` (`user_id`)
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
-- Estructura de tabla para la tabla `Process`
--

CREATE TABLE IF NOT EXISTS `Process` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `algorithm` varchar(255) DEFAULT NULL,
  `filterCondition` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `examples_id` int(11) DEFAULT NULL,
  `results_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_1twfjjwiogcv5hvdmxafyc3ip` (`examples_id`),
  KEY `FK_n6cyrli24gvwxlpqe825riyf7` (`results_id`)
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
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Examples`
--
ALTER TABLE `Examples`
  ADD CONSTRAINT `FK_cqtkdv36vuws0k5w57c967k6r` FOREIGN KEY (`user_id`) REFERENCES `User` (`id`);

--
-- Filtros para la tabla `Process`
--
ALTER TABLE `Process`
  ADD CONSTRAINT `FK_n6cyrli24gvwxlpqe825riyf7` FOREIGN KEY (`results_id`) REFERENCES `Results` (`id`),
  ADD CONSTRAINT `FK_1twfjjwiogcv5hvdmxafyc3ip` FOREIGN KEY (`examples_id`) REFERENCES `Examples` (`id`);

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
