-- phpMyAdmin SQL Dump
-- version 4.4.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 18, 2016 at 03:49 PM
-- Server version: 5.6.26
-- PHP Version: 5.6.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `baby_health_care`
--

-- --------------------------------------------------------

--
-- Table structure for table `child`
--

CREATE TABLE IF NOT EXISTS `child` (
  `id_child` int(11) unsigned NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `first_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `last_name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `birth_date` date NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `pic` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `child`
--

INSERT INTO `child` (`id_child`, `user_name`, `first_name`, `last_name`, `birth_date`, `gender`, `pic`) VALUES
(1, 'child1', 'c1', 'c1', '2016-04-20', 'male', ''),
(2, 'child2', 'c2', 'c2', '2016-04-01', 'female', ''),
(3, 'child3', 'c3', 'c3', '2016-04-20', 'male', ''),
(4, 'child4', 'c4', 'c4', '2016-04-01', 'female', '');

-- --------------------------------------------------------

--
-- Table structure for table `child_doctors`
--

CREATE TABLE IF NOT EXISTS `child_doctors` (
  `id_child_doctors` int(11) NOT NULL,
  `id_doctor` int(11) unsigned NOT NULL,
  `id_child` int(11) unsigned NOT NULL,
  `approved` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `child_doctors`
--

INSERT INTO `child_doctors` (`id_child_doctors`, `id_doctor`, `id_child`, `approved`) VALUES
(1, 6, 1, 0),
(2, 6, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `child_parents`
--

CREATE TABLE IF NOT EXISTS `child_parents` (
  `id` int(11) NOT NULL,
  `id_parent` int(11) unsigned NOT NULL,
  `id_child` int(11) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `food`
--

CREATE TABLE IF NOT EXISTS `food` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `quantity` int(11) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `id_child` int(11) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `groups`
--

CREATE TABLE IF NOT EXISTS `groups` (
  `id` mediumint(8) unsigned NOT NULL,
  `name` varchar(20) NOT NULL,
  `description` varchar(100) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `groups`
--

INSERT INTO `groups` (`id`, `name`, `description`) VALUES
(1, 'admin', 'Administrator'),
(2, 'members', 'General User'),
(3, 'new_group', 'no');

-- --------------------------------------------------------

--
-- Table structure for table `guid`
--

CREATE TABLE IF NOT EXISTS `guid` (
  `id` int(11) unsigned NOT NULL,
  `id_main_section` int(11) unsigned NOT NULL,
  `age` int(11) unsigned NOT NULL,
  `description` text CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `login_attempts`
--

CREATE TABLE IF NOT EXISTS `login_attempts` (
  `id` int(11) unsigned NOT NULL,
  `ip_address` varchar(15) NOT NULL,
  `login` varchar(100) NOT NULL,
  `time` int(11) unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `main_section`
--

CREATE TABLE IF NOT EXISTS `main_section` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `main_section`
--

INSERT INTO `main_section` (`id`, `name`) VALUES
(1, 'measurements'),
(2, 'food'),
(3, 'sleep');

-- --------------------------------------------------------

--
-- Table structure for table `main_section_details`
--

CREATE TABLE IF NOT EXISTS `main_section_details` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(50) NOT NULL,
  `icon` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `id_main_section` int(11) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `quantities`
--

CREATE TABLE IF NOT EXISTS `quantities` (
  `id` int(11) unsigned NOT NULL,
  `name` int(11) NOT NULL,
  `unit` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `quantities_child`
--

CREATE TABLE IF NOT EXISTS `quantities_child` (
  `id` int(11) unsigned NOT NULL,
  `id_child` int(11) unsigned NOT NULL,
  `id_quantities` int(11) unsigned NOT NULL,
  `amount` int(11) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sleep`
--

CREATE TABLE IF NOT EXISTS `sleep` (
  `id` int(11) unsigned NOT NULL,
  `from_date` datetime NOT NULL,
  `to_date` datetime NOT NULL,
  `id_child` int(11) unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) unsigned NOT NULL,
  `ip_address` varchar(45) NOT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `activation_code` varchar(40) DEFAULT NULL,
  `forgotten_password_code` varchar(40) DEFAULT NULL,
  `forgotten_password_time` int(11) unsigned DEFAULT NULL,
  `remember_code` varchar(40) DEFAULT NULL,
  `created_on` int(11) unsigned NOT NULL,
  `last_login` int(11) unsigned DEFAULT NULL,
  `active` tinyint(1) unsigned DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `company` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `birth_date` date NOT NULL,
  `gender` enum('male','female') NOT NULL,
  `pic` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `location` varchar(255) NOT NULL,
  `id_role` int(11) unsigned NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `ip_address`, `username`, `password`, `salt`, `email`, `activation_code`, `forgotten_password_code`, `forgotten_password_time`, `remember_code`, `created_on`, `last_login`, `active`, `first_name`, `last_name`, `company`, `phone`, `birth_date`, `gender`, `pic`, `location`, `id_role`) VALUES
(1, '127.0.0.1', 'administrator', '$2y$08$t7r5eKrqNjI9k5.Z0ntDgOi4zAta5ZkVZsA2pXJ.bjWnecU.POQvC', NULL, 'admin@admin.com', NULL, NULL, NULL, NULL, 1970, 1460984479, 1, 'Admin', 'istrator', 'ADMIN', '0', '2016-04-01', 'male', '52mahs4fpcw0skk.jpg', '33.5138073,36.27652790000002', 1),
(6, '::1', 'aaaaaaaaaaaaa', '$2y$08$QOxxZAgdYLk6AassZrMaR.Pje67KlneFYa.1gnGAUVLrpwJTixb1a', NULL, 'usama@gmail.com', NULL, NULL, NULL, NULL, 1970, 1460984505, 1, 'usama', 'baghdady', '0', '0', '2016-04-22', 'male', '7setgxdchp8g4c8.jpg', '33.5138073,36.27652790000002', 2);

-- --------------------------------------------------------

--
-- Table structure for table `users_groups`
--

CREATE TABLE IF NOT EXISTS `users_groups` (
  `id` int(11) unsigned NOT NULL,
  `user_id` int(11) unsigned NOT NULL,
  `group_id` mediumint(8) unsigned NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users_groups`
--

INSERT INTO `users_groups` (`id`, `user_id`, `group_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(9, 6, 2);

-- --------------------------------------------------------

--
-- Table structure for table `user_permissions`
--

CREATE TABLE IF NOT EXISTS `user_permissions` (
  `id` int(11) unsigned NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_permissions`
--

INSERT INTO `user_permissions` (`id`, `description`) VALUES
(1, 'what ever');

-- --------------------------------------------------------

--
-- Table structure for table `user_role`
--

CREATE TABLE IF NOT EXISTS `user_role` (
  `id` int(11) unsigned NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_role`
--

INSERT INTO `user_role` (`id`, `name`) VALUES
(1, 'Admin'),
(2, 'Doctor'),
(3, 'Parent');

-- --------------------------------------------------------

--
-- Table structure for table `user_role_permissions`
--

CREATE TABLE IF NOT EXISTS `user_role_permissions` (
  `id` int(11) NOT NULL,
  `id_role` int(11) unsigned NOT NULL,
  `id_permission` int(11) unsigned NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user_role_permissions`
--

INSERT INTO `user_role_permissions` (`id`, `id_role`, `id_permission`) VALUES
(2, 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `child`
--
ALTER TABLE `child`
  ADD PRIMARY KEY (`id_child`);

--
-- Indexes for table `child_doctors`
--
ALTER TABLE `child_doctors`
  ADD PRIMARY KEY (`id_child_doctors`),
  ADD KEY `id_doctor` (`id_doctor`),
  ADD KEY `id_child` (`id_child`);

--
-- Indexes for table `child_parents`
--
ALTER TABLE `child_parents`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_parent` (`id_parent`),
  ADD KEY `id_child` (`id_child`);

--
-- Indexes for table `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_child` (`id_child`);

--
-- Indexes for table `groups`
--
ALTER TABLE `groups`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `guid`
--
ALTER TABLE `guid`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_main_section` (`id_main_section`);

--
-- Indexes for table `login_attempts`
--
ALTER TABLE `login_attempts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `main_section`
--
ALTER TABLE `main_section`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `main_section_details`
--
ALTER TABLE `main_section_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_main_section` (`id_main_section`);

--
-- Indexes for table `quantities`
--
ALTER TABLE `quantities`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `quantities_child`
--
ALTER TABLE `quantities_child`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_child` (`id_child`),
  ADD KEY `id_quantities` (`id_quantities`);

--
-- Indexes for table `sleep`
--
ALTER TABLE `sleep`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_child` (`id_child`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_role` (`id_role`);

--
-- Indexes for table `users_groups`
--
ALTER TABLE `users_groups`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uc_users_groups` (`user_id`,`group_id`),
  ADD KEY `fk_users_groups_users1_idx` (`user_id`),
  ADD KEY `fk_users_groups_groups1_idx` (`group_id`);

--
-- Indexes for table `user_permissions`
--
ALTER TABLE `user_permissions`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_role`
--
ALTER TABLE `user_role`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_role_permissions`
--
ALTER TABLE `user_role_permissions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_role` (`id_role`),
  ADD KEY `id_permission` (`id_permission`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `child`
--
ALTER TABLE `child`
  MODIFY `id_child` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `child_doctors`
--
ALTER TABLE `child_doctors`
  MODIFY `id_child_doctors` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `child_parents`
--
ALTER TABLE `child_parents`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `food`
--
ALTER TABLE `food`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `groups`
--
ALTER TABLE `groups`
  MODIFY `id` mediumint(8) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `guid`
--
ALTER TABLE `guid`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `login_attempts`
--
ALTER TABLE `login_attempts`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `main_section`
--
ALTER TABLE `main_section`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `main_section_details`
--
ALTER TABLE `main_section_details`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `quantities`
--
ALTER TABLE `quantities`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `quantities_child`
--
ALTER TABLE `quantities_child`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `sleep`
--
ALTER TABLE `sleep`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `users_groups`
--
ALTER TABLE `users_groups`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `user_permissions`
--
ALTER TABLE `user_permissions`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `user_role`
--
ALTER TABLE `user_role`
  MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `user_role_permissions`
--
ALTER TABLE `user_role_permissions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `child_doctors`
--
ALTER TABLE `child_doctors`
  ADD CONSTRAINT `child_doctors_ibfk_1` FOREIGN KEY (`id_doctor`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `child_doctors_ibfk_2` FOREIGN KEY (`id_child`) REFERENCES `child` (`id_child`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `child_parents`
--
ALTER TABLE `child_parents`
  ADD CONSTRAINT `child_parents_ibfk_1` FOREIGN KEY (`id_parent`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `child_parents_ibfk_2` FOREIGN KEY (`id_child`) REFERENCES `child` (`id_child`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `food`
--
ALTER TABLE `food`
  ADD CONSTRAINT `food_ibfk_1` FOREIGN KEY (`id_child`) REFERENCES `child` (`id_child`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `guid`
--
ALTER TABLE `guid`
  ADD CONSTRAINT `guid_ibfk_1` FOREIGN KEY (`id_main_section`) REFERENCES `main_section` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `main_section_details`
--
ALTER TABLE `main_section_details`
  ADD CONSTRAINT `main_section_details_ibfk_1` FOREIGN KEY (`id_main_section`) REFERENCES `main_section` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `quantities_child`
--
ALTER TABLE `quantities_child`
  ADD CONSTRAINT `quantities_child_ibfk_2` FOREIGN KEY (`id_quantities`) REFERENCES `quantities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `quantities_child_ibfk_3` FOREIGN KEY (`id_child`) REFERENCES `child` (`id_child`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sleep`
--
ALTER TABLE `sleep`
  ADD CONSTRAINT `sleep_ibfk_1` FOREIGN KEY (`id_child`) REFERENCES `child` (`id_child`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`id_role`) REFERENCES `user_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `users_groups`
--
ALTER TABLE `users_groups`
  ADD CONSTRAINT `fk_users_groups_groups1` FOREIGN KEY (`group_id`) REFERENCES `groups` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  ADD CONSTRAINT `fk_users_groups_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;

--
-- Constraints for table `user_role_permissions`
--
ALTER TABLE `user_role_permissions`
  ADD CONSTRAINT `user_role_permissions_ibfk_1` FOREIGN KEY (`id_role`) REFERENCES `user_role` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_role_permissions_ibfk_2` FOREIGN KEY (`id_permission`) REFERENCES `user_permissions` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
