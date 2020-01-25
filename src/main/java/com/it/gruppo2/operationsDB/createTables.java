package com.it.gruppo2.operationsDB;

import java.sql.*;

// creating a tables
public class createTables {
	public void setTables(Connection connection) throws SQLException {
		Statement stmt = connection.createStatement();
		System.out.println("Creating table in given database...");
		String sql = "CREATE TABLE `attrezzatura` (\r\n" + 
				"  `id_attrezzatura` int(11) NOT NULL,\r\n" + 
				"  `nome` varchar(20) NOT NULL,\r\n" + 
				"  `capacita` int(11) NOT NULL,\r\n" + 
				"  `id_birraio` int(11) NOT NULL\r\n" + 
				")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE `birra` (\r\n" + 
				"  `id_birra` int(11) NOT NULL,\r\n" + 
				"  `nome` varchar(20) NOT NULL,\r\n" + 
				"  `tipo` varchar(20) NOT NULL,\r\n" + 
				"  `id_birraio` int(11) NOT NULL\r\n" + 
				")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE `birraio` (\r\n" + 
				"  `id_birraio` int(11) NOT NULL,\r\n" + 
				"  `nome` varchar(15) NOT NULL,\r\n" + 
				"  `cognome` varchar(15) NOT NULL,\r\n" + 
				"  `username` varchar(15) NOT NULL,\r\n" + 
				"  `password` varchar(15) NOT NULL\r\n" + 
				")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE `dispensa` (\r\n" + 
				"  `qta` double DEFAULT NULL,\r\n" + 
				"  `id_ingrediente` int(11) NOT NULL,\r\n" + 
				"  `id_birraio` int(11) NOT NULL\r\n" + 
				")";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE `ingrediente` (\r\n" + 
				"  `nome` varchar(15) NOT NULL,\r\n" + 
				"  `id_ingrediente` int(11) NOT NULL,\r\n" + 
				"  `tipo` enum('zucchero','lievito','additivi','malto','luppolo') NOT NULL\r\n" +
				")";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `attrezzatura`\r\n" + 
				"  ADD PRIMARY KEY (`id_attrezzatura`),\r\n" + 
				"  ADD KEY `id_birraio` (`id_birraio`);";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `birra`\r\n" + 
				"  ADD PRIMARY KEY (`id_birra`),\r\n" + 
				"  ADD KEY `birra_ibfk_1` (`id_birraio`);";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `birraio`\r\n" + 
				"  ADD PRIMARY KEY (`id_birraio`);";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `dispensa`\r\n" + 
				"  ADD PRIMARY KEY (`id_ingrediente`,`id_birraio`),\r\n" + 
				"  ADD KEY `id_birraio` (`id_birraio`);";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `ingrediente`\r\n" + 
				"  ADD PRIMARY KEY (`id_ingrediente`);";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `attrezzatura`\r\n" + 
				"  MODIFY `id_attrezzatura` int(11) NOT NULL AUTO_INCREMENT;";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `birra`\r\n" + 
				"  MODIFY `id_birra` int(11) NOT NULL AUTO_INCREMENT;";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `birraio`\r\n" + 
				"  MODIFY `id_birraio` int(11) NOT NULL AUTO_INCREMENT;";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `ingrediente`\r\n" + 
				"  MODIFY `id_ingrediente` int(11) NOT NULL AUTO_INCREMENT;";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `birra`\r\n" + 
				"  ADD CONSTRAINT `birra_ibfk_1` FOREIGN KEY (`id_birraio`) REFERENCES `birraio` (`id_birraio`) ON DELETE NO ACTION ON UPDATE CASCADE;";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `dispensa`\r\n" + 
				"  ADD CONSTRAINT `dispensa_ibfk_1` FOREIGN KEY (`id_ingrediente`) REFERENCES `ingrediente` (`id_ingrediente`),\r\n" + 
				"  ADD CONSTRAINT `dispensa_ibfk_2` FOREIGN KEY (`id_birraio`) REFERENCES `birraio` (`id_birraio`);";
		stmt.executeUpdate(sql);
		sql = "CREATE TABLE `ricetta` (\r\n" + 
				"  `id_birra` int(11) NOT NULL,\r\n" + 
				"  `id_ingrediente` int(11) NOT NULL,\r\n" + 
				"  `quantita` double NULL\r\n" + 
				")";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `ricetta`\r\n" + 
				"  ADD PRIMARY KEY (`id_birra`,`id_ingrediente`),\r\n" + 
				"  ADD KEY `idfk_id_ingrediente` (`id_ingrediente`);";
		stmt.executeUpdate(sql);
		sql = "ALTER TABLE `ricetta`\r\n" + 
				"  ADD CONSTRAINT `idfk_id_birra` FOREIGN KEY (`id_birra`) REFERENCES `birra` (`id_birra`),\r\n" + 
				"  ADD CONSTRAINT `idfk_id_ingrediente` FOREIGN KEY (`id_ingrediente`) REFERENCES `ingrediente` (`id_ingrediente`);";
		stmt.executeUpdate(sql);
		System.out.println("Created table in given database..");
	}
}
