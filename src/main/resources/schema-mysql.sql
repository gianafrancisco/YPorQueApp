-- phpMyAdmin SQL Dump
-- version 4.3.12
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 12-01-2016 a las 22:16:45
-- Versión del servidor: 5.5.46-0+deb7u1
-- Versión de PHP: 5.4.45-0+deb7u2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `yporque`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `articulo`
--

CREATE TABLE IF NOT EXISTS `articulo` (
  `articulo_id` bigint(20) NOT NULL,
  `precio_lista` double NOT NULL,
  `factor1` double NOT NULL,
  `factor2` double NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `cantidad_stock` bigint(20) NOT NULL,
  `codigo` varchar(256) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `caja`
--

CREATE TABLE IF NOT EXISTS `caja` (
  `caja_id` bigint(20) NOT NULL,
  `apertura` datetime DEFAULT NULL,
  `apertura_username` varchar(100) NOT NULL,
  `cierre` datetime DEFAULT NULL,
  `cierre_username` varchar(100) NOT NULL,
  `efectivo` double NOT NULL,
  `tarjeta` double NOT NULL,
  `total_venta_dia` double NOT NULL,
  `efectivo_dia_siguiente` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `retiro`
--

CREATE TABLE IF NOT EXISTS `retiro` (
  `retiro_id` bigint(20) NOT NULL,
  `monto` double NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `username` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vendedor`
--

CREATE TABLE IF NOT EXISTS `vendedor` (
  `vendedor_id` bigint(20) NOT NULL,
  `username` varchar(100) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `venta`
--

CREATE TABLE IF NOT EXISTS `venta` (
  `venta_id` bigint(20) NOT NULL,
  `fecha` datetime DEFAULT NULL,
  `codigo` varchar(256) NOT NULL,
  `descripcion` varchar(512) NOT NULL,
  `cantidad` bigint(20) NOT NULL,
  `factor1` double NOT NULL,
  `factor2` double NOT NULL,
  `precio_lista` double NOT NULL,
  `precio` double NOT NULL,
  `tipo_pago` varchar(100) NOT NULL,
  `username` varchar(100) NOT NULL,
  `nro_cupon` varchar(100) NOT NULL,
  `codigo_devolucion` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `articulo`
--
ALTER TABLE `articulo`
  ADD PRIMARY KEY (`articulo_id`), ADD UNIQUE KEY `articulo_id` (`articulo_id`), ADD KEY `codigo` (`codigo`);

--
-- Indices de la tabla `caja`
--
ALTER TABLE `caja`
  ADD PRIMARY KEY (`caja_id`), ADD UNIQUE KEY `caja_id` (`caja_id`);

--
-- Indices de la tabla `retiro`
--
ALTER TABLE `retiro`
  ADD PRIMARY KEY (`retiro_id`), ADD UNIQUE KEY `retido_id` (`retiro_id`);

--
-- Indices de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  ADD PRIMARY KEY (`vendedor_id`), ADD UNIQUE KEY `vendedor_id` (`vendedor_id`);

--
-- Indices de la tabla `venta`
--
ALTER TABLE `venta`
  ADD PRIMARY KEY (`venta_id`), ADD UNIQUE KEY `venta_id` (`venta_id`), ADD KEY `fecha` (`fecha`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `articulo`
--
ALTER TABLE `articulo`
  MODIFY `articulo_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `caja`
--
ALTER TABLE `caja`
  MODIFY `caja_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `retiro`
--
ALTER TABLE `retiro`
  MODIFY `retiro_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `vendedor`
--
ALTER TABLE `vendedor`
  MODIFY `vendedor_id` bigint(20) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT de la tabla `venta`
--
ALTER TABLE `venta`
  MODIFY `venta_id` bigint(20) NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
