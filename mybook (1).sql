-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Feb 01, 2025 at 12:59 AM
-- Server version: 8.0.31
-- PHP Version: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mybook`
--

-- --------------------------------------------------------

--
-- Table structure for table `aubooks`
--

DROP TABLE IF EXISTS `aubooks`;
CREATE TABLE IF NOT EXISTS `aubooks` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `books_id` bigint NOT NULL,
  `author_id` bigint NOT NULL,
  `book_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `author_id` (`author_id`),
  KEY `FKq335akble0umbqdmmjvhm7xmp` (`book_id`),
  KEY `FKexshu72p23yoa0no848lcvxfi` (`books_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
CREATE TABLE IF NOT EXISTS `author` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `author`
--

INSERT INTO `author` (`id`, `name`) VALUES
(6, 'Donald Knuth'),
(7, 'Alan Turing'),
(8, 'Agatha Christie'),
(9, 'Jamie Oliver'),
(10, 'Carl Sagan'),
(11, 'Mary Beard'),
(12, 'Athina Latifi');

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `isbn` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `stock` int DEFAULT '0',
  `summary` varchar(255) DEFAULT NULL,
  `author` varchar(255) DEFAULT NULL,
  `borrow_count` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `isbn`, `title`, `stock`, `summary`, `author`, `borrow_count`) VALUES
(6, '978-3-16-148410-0', 'The Art of Programming', 5, 'An essential guide to mastering programming techniques.', 'Carl Sagan', NULL),
(8, '978-0-596-52068-7', 'Cooking Made Simple', 19, 'A beginner-friendly cookbook with easy and delicious recipes.', NULL, NULL),
(10, '978-0-14-044926-6', 'The Ancient World', 7, 'A fascinating look at the civilizations of the ancient past.', 'Carl ', NULL),
(13, '978-3-16-148410-0', 'The Great Gatsby', 0, 'A novel set in the Roaring Twenties that tells the story of Jay Gatsby\'s unrequited love for Daisy Buchanan.', 'F. Scott Fitzgerald', NULL),
(14, '978-0-7432-7356-5', 'To Kill a Mockingbird', 0, 'A coming-of-age story that deals with serious issues like racial injustice and moral growth in the Deep South.', 'Harper Lee', NULL),
(15, '978-0-452-28423-4', '1984', 0, 'A dystopian novel that explores the dangers of totalitarianism and extreme political ideology.', 'George Orwell', NULL),
(16, '978-0-06-112008-4', 'The Catcher in the Rye', 0, 'A story about teenage angst and alienation, narrated by the iconic character Holden Caulfield.', 'J.D. Salinger', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `books_borrowed`
--

DROP TABLE IF EXISTS `books_borrowed`;
CREATE TABLE IF NOT EXISTS `books_borrowed` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `book_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `return_date` date DEFAULT NULL,
  `borrowed_date` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userrr` (`user_id`),
  KEY `booksss` (`book_id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `books_borrowed`
--

INSERT INTO `books_borrowed` (`id`, `book_id`, `user_id`, `return_date`, `borrowed_date`) VALUES
(19, 8, 4, '2025-02-13', '2025-01-30'),
(20, 6, 7, '2025-02-13', '2025-01-30');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `email`, `role`) VALUES
(2, 'admin_user', 'admin123', 'admin@example.com', 'ADMIN'),
(3, 'librarian_user', 'libpassword', 'librarian@example.com', 'LIBRARIAN'),
(4, 'athina', 'athina123', 'athina@gmail.com', 'USER'),
(5, 'alex', 'alex123', 'alex@gmail.com', 'ADMIN'),
(7, 'maria', 'maria123', 'maria@gmail.com', 'USER');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `aubooks`
--
ALTER TABLE `aubooks`
  ADD CONSTRAINT `aubooks_ibfk_1` FOREIGN KEY (`books_id`) REFERENCES `books` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `aubooks_ibfk_2` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKlh3qr1rxh98wknokwjf22i1m` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`);

--
-- Constraints for table `books_borrowed`
--
ALTER TABLE `books_borrowed`
  ADD CONSTRAINT `booksss` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `userrr` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
