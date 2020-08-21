-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mer. 29 juil. 2020 à 17:38
-- Version du serveur :  10.4.13-MariaDB
-- Version de PHP : 7.2.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données : `open-food-facts`
--
CREATE DATABASE IF NOT EXISTS `open-food-facts` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `open-food-facts`;

-- --------------------------------------------------------

--
-- Structure de la table `additif`
--

DROP TABLE IF EXISTS `additif`;
CREATE TABLE IF NOT EXISTS `additif` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `allergene`
--

DROP TABLE IF EXISTS `allergene`;
CREATE TABLE IF NOT EXISTS `allergene` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `article`
--

DROP TABLE IF EXISTS `article`;
CREATE TABLE IF NOT EXISTS `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_cat` int(11) NOT NULL,
  `id_mar` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `grade` varchar(55) NOT NULL,
  `energie100g` double NOT NULL,
  `graisse100g` double NOT NULL,
  `sucres100g` double NOT NULL,
  `fibres100g` double NOT NULL,
  `proteines100g` double NOT NULL,
  `sel100g` double NOT NULL,
  `vitA100g` double NOT NULL,
  `vitD100g` double NOT NULL,
  `vitE100g` double NOT NULL,
  `vitK100g` double NOT NULL,
  `vitC100g` double NOT NULL,
  `vitB1100g` double NOT NULL,
  `vitB2100g` double NOT NULL,
  `vitPP100g` double NOT NULL,
  `vitB6100g` double NOT NULL,
  `vitB9100g` double NOT NULL,
  `vitB12100g` double NOT NULL,
  `calcium100g` double NOT NULL,
  `magnesium100g` double NOT NULL,
  `iron100g` double NOT NULL,
  `fer100g` double NOT NULL,
  `betaCarotene100g` double NOT NULL,
  `presenceHuilePalme` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_article_categorie` (`id_cat`),
  KEY `FK_article_marque` (`id_mar`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `article_additif`
--

DROP TABLE IF EXISTS `article_additif`;
CREATE TABLE IF NOT EXISTS `article_additif` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_art` int(11) NOT NULL,
  `id_add` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_article_additif_article` (`id_art`),
  KEY `FK_article_additif_additif` (`id_add`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `article_allergene`
--

DROP TABLE IF EXISTS `article_allergene`;
CREATE TABLE IF NOT EXISTS `article_allergene` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_art` int(11) NOT NULL,
  `id_all` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_article_allergene_article` (`id_art`),
  KEY `FK_article_allergene_allergene` (`id_all`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `article_ingredient`
--

DROP TABLE IF EXISTS `article_ingredient`;
CREATE TABLE IF NOT EXISTS `article_ingredient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_art` int(11) NOT NULL,
  `id_ing` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_article_ingredient_article` (`id_art`),
  KEY `FK_article_ingredient_ingredient` (`id_ing`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `ingredient`
--

DROP TABLE IF EXISTS `ingredient`;
CREATE TABLE IF NOT EXISTS `ingredient` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `marque`
--

DROP TABLE IF EXISTS `marque`;
CREATE TABLE IF NOT EXISTS `marque` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `article`
--
ALTER TABLE `article`
  ADD CONSTRAINT `FK_article_categorie` FOREIGN KEY (`id_cat`) REFERENCES `categorie` (`id`),
  ADD CONSTRAINT `FK_article_marque` FOREIGN KEY (`id_mar`) REFERENCES `marque` (`id`);

--
-- Contraintes pour la table `article_additif`
--
ALTER TABLE `article_additif`
  ADD CONSTRAINT `FK_article_additif_additif` FOREIGN KEY (`id_add`) REFERENCES `additif` (`id`),
  ADD CONSTRAINT `FK_article_additif_article` FOREIGN KEY (`id_art`) REFERENCES `article` (`id`);

--
-- Contraintes pour la table `article_allergene`
--
ALTER TABLE `article_allergene`
  ADD CONSTRAINT `FK_article_allergene_allergene` FOREIGN KEY (`id_all`) REFERENCES `allergene` (`id`),
  ADD CONSTRAINT `FK_article_allergene_article` FOREIGN KEY (`id_art`) REFERENCES `article` (`id`);

--
-- Contraintes pour la table `article_ingredient`
--
ALTER TABLE `article_ingredient`
  ADD CONSTRAINT `FK_article_ingredient_article` FOREIGN KEY (`id_art`) REFERENCES `article` (`id`),
  ADD CONSTRAINT `FK_article_ingredient_ingredient` FOREIGN KEY (`id_ing`) REFERENCES `ingredient` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
