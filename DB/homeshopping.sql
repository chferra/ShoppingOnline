-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 31, 2020 at 05:30 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `homeshopping`
--

-- --------------------------------------------------------

--
-- Table structure for table `addresses`
--

CREATE TABLE `addresses` (
  `ID` int(8) NOT NULL,
  `addressee` varchar(32) DEFAULT NULL,
  `phone` varchar(16) DEFAULT NULL,
  `Country` varchar(16) DEFAULT NULL,
  `province` char(2) DEFAULT NULL,
  `city` varchar(16) DEFAULT NULL,
  `street` varchar(16) DEFAULT NULL,
  `number` int(2) DEFAULT NULL,
  `zipCode` char(5) DEFAULT NULL,
  `IdUser` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `carts`
--

CREATE TABLE `carts` (
  `ID` int(8) NOT NULL,
  `IdUser` int(8) DEFAULT NULL,
  `IdShop` int(8) DEFAULT NULL,
  `orderPlaced` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `carts_composition`
--

CREATE TABLE `carts_composition` (
  `ID` int(8) NOT NULL,
  `IdCart` int(8) DEFAULT NULL,
  `IdProduct` int(8) DEFAULT NULL,
  `quantityCustomer` int(4) DEFAULT NULL,
  `quantityVendor` int(4) DEFAULT NULL,
  `quantityConfirmed` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `ID` int(8) NOT NULL,
  `name` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `ID` int(8) NOT NULL,
  `dateTimePurchase` datetime DEFAULT NULL,
  `dateTimeDelivery` datetime DEFAULT NULL,
  `homeDelivery` tinyint(1) DEFAULT NULL,
  `invoice` tinyint(1) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `customerConfirm` tinyint(1) DEFAULT NULL,
  `vendorConfirm` tinyint(1) DEFAULT NULL,
  `IdCart` int(8) DEFAULT NULL,
  `IdShippingAddress` int(8) DEFAULT NULL,
  `IdShop` int(8) DEFAULT NULL,
  `IdUser` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `pictures`
--

CREATE TABLE `pictures` (
  `ID` int(8) NOT NULL,
  `path` varchar(32) NOT NULL,
  `IdShop` int(8) DEFAULT NULL,
  `IdProduct` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `ID` int(8) NOT NULL,
  `name` varchar(32) NOT NULL,
  `description` varchar(128) DEFAULT NULL,
  `price` int(4) NOT NULL,
  `IdShop` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `products_enrollment`
--

CREATE TABLE `products_enrollment` (
  `IdProduct` int(8) NOT NULL,
  `IdCategory` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `shop`
--

CREATE TABLE `shop` (
  `ID` int(8) NOT NULL,
  `name` varchar(32) DEFAULT NULL,
  `IdUser` int(8) NOT NULL,
  `IdAddress` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `shops_enrollment`
--

CREATE TABLE `shops_enrollment` (
  `IdShop` int(8) NOT NULL,
  `IdCategory` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(8) NOT NULL,
  `name` varchar(16) NOT NULL,
  `surname` varchar(16) NOT NULL,
  `birthDate` date NOT NULL,
  `email` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `vendor` tinyint(1) NOT NULL,
  `IdMainAddress` int(8) DEFAULT NULL,
  `IdProfilePic` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `carts_composition`
--
ALTER TABLE `carts_composition`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `pictures`
--
ALTER TABLE `pictures`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `products_enrollment`
--
ALTER TABLE `products_enrollment`
  ADD PRIMARY KEY (`IdProduct`,`IdCategory`);

--
-- Indexes for table `shop`
--
ALTER TABLE `shop`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `shops_enrollment`
--
ALTER TABLE `shops_enrollment`
  ADD PRIMARY KEY (`IdShop`,`IdCategory`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `ID` int(8) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(8) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
