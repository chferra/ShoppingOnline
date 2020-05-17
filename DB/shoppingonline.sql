-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Mag 17, 2020 alle 18:01
-- Versione del server: 10.4.8-MariaDB
-- Versione PHP: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shoppingonline`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `appartiene`
--

CREATE TABLE `appartiene` (
  `CODPRODOTTO` int(11) DEFAULT NULL,
  `CODCATEGORIA` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `caratteristica`
--

CREATE TABLE `caratteristica` (
  `ID` int(11) NOT NULL,
  `nome` varchar(32) DEFAULT NULL,
  `valore` int(10) DEFAULT NULL,
  `CODPRODOTTO` int(11) DEFAULT NULL,
  `CodUtente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `caratterizza`
--

CREATE TABLE `caratterizza` (
  `CodNegozio` int(11) DEFAULT NULL,
  `CodCategoria` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `carrello`
--

CREATE TABLE `carrello` (
  `ID` int(11) NOT NULL,
  `CodUtente` int(11) DEFAULT NULL,
  `CodNegozio` int(11) DEFAULT NULL,
  `ordineEffettuato` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `categoria`
--

CREATE TABLE `categoria` (
  `ID` int(11) NOT NULL,
  `nome` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `comprende`
--

CREATE TABLE `comprende` (
  `ID` int(11) NOT NULL,
  `CodNegozio` int(11) DEFAULT NULL,
  `CodCarrello` int(11) DEFAULT NULL,
  `CodProdotto` int(11) DEFAULT NULL,
  `quantitaCliente` int(10) DEFAULT NULL,
  `quantitaNegoziante` int(11) DEFAULT NULL,
  `quantitaConfermata` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `fotografia`
--

CREATE TABLE `fotografia` (
  `ID` int(11) NOT NULL,
  `percorso` varchar(32) NOT NULL,
  `CodNegozio` int(11) DEFAULT NULL,
  `CodProdotto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `indirizzo`
--

CREATE TABLE `indirizzo` (
  `ID` int(11) NOT NULL,
  `destinatario` varchar(32) DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL,
  `Stato` varchar(32) DEFAULT NULL,
  `provincia` varchar(2) DEFAULT NULL,
  `citta` varchar(10) DEFAULT NULL,
  `via` varchar(10) DEFAULT NULL,
  `nCivico` int(2) DEFAULT NULL,
  `codPostale` varchar(5) DEFAULT NULL,
  `CodUtente` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `negozio`
--

CREATE TABLE `negozio` (
  `ID` int(11) NOT NULL,
  `nome` varchar(32) DEFAULT NULL,
  `CodIndirizzo` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `ordine`
--

CREATE TABLE `ordine` (
  `ID` int(11) NOT NULL,
  `dataOraAcquisto` datetime DEFAULT NULL,
  `dataOraConsegna` datetime DEFAULT NULL,
  `consegnaDomicilio` tinyint(1) DEFAULT NULL,
  `fattura` varchar(32) DEFAULT NULL,
  `stato` varchar(32) DEFAULT NULL,
  `confermaCliente` tinyint(1) DEFAULT NULL,
  `confermaNegoziante` tinyint(1) DEFAULT NULL,
  `CodCarrello` int(11) DEFAULT NULL,
  `CodIndirizzoConsegna` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `prodotto`
--

CREATE TABLE `prodotto` (
  `ID` int(11) NOT NULL,
  `nome` varchar(32) DEFAULT NULL,
  `descrizione` varchar(32) DEFAULT NULL,
  `prezzo` int(10) DEFAULT NULL,
  `CodUtente` int(11) DEFAULT NULL,
  `CodNegozio` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

CREATE TABLE `utenti` (
  `ID` int(8) NOT NULL,
  `nome` varchar(16) NOT NULL,
  `cognome` varchar(16) NOT NULL,
  `dataNascita` date NOT NULL,
  `email` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `negoziante` tinyint(1) NOT NULL,
  `CodIndirizzoPredefinito` int(11) DEFAULT NULL,
  `CodFotografia` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `caratteristica`
--
ALTER TABLE `caratteristica`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `carrello`
--
ALTER TABLE `carrello`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `comprende`
--
ALTER TABLE `comprende`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `fotografia`
--
ALTER TABLE `fotografia`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `indirizzo`
--
ALTER TABLE `indirizzo`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `negozio`
--
ALTER TABLE `negozio`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `ordine`
--
ALTER TABLE `ordine`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `prodotto`
--
ALTER TABLE `prodotto`
  ADD PRIMARY KEY (`ID`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `indirizzo`
--
ALTER TABLE `indirizzo`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT per la tabella `utenti`
--
ALTER TABLE `utenti`
  MODIFY `ID` int(8) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
