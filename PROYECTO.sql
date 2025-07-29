-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-07-2025 a las 03:38:24
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_de_notas_escolares`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudiantes`
--

CREATE TABLE `estudiantes` (
  `idEstudiante` int(11) NOT NULL,
  `cedula` varchar(10) NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `estudiantes`
--

INSERT INTO `estudiantes` (`idEstudiante`, `cedula`, `nombres`, `apellidos`) VALUES
(1, '1712067485', 'FANNY AURORA', 'CANGO QUITO'),
(2, '1722169032', 'ANDREA ABIGAIL', 'CAISA CANGO'),
(3, '1720871506', 'JOSSELYN ANDREA', 'QUINOZ'),
(4, '1222222222', 'ERICKA PAULINA', 'CAISA CANGO'),
(5, '1333333333', 'AURORA PAULINA', 'CAISA CANGO'),
(6, '1000000000', 'SEGUNDO RAUL ', 'CAISA PILLIZA'),
(7, '1234567832', 'CAMILA FANNY', 'CORREA LEON'),
(8, '1245711212', 'ELENA LUNA', 'CANGO LEMA'),
(9, '12457896', 'LAURA LUNA', 'CUSTA QUITO');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inscripciones`
--

CREATE TABLE `inscripciones` (
  `idInscripcion` int(11) NOT NULL,
  `idEstudiante` int(11) NOT NULL,
  `idMateria` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `inscripciones`
--

INSERT INTO `inscripciones` (`idInscripcion`, `idEstudiante`, `idMateria`) VALUES
(1, 1, 1),
(11, 1, 2),
(3, 2, 1),
(4, 2, 3),
(2, 3, 2),
(8, 4, 1),
(9, 4, 3),
(5, 5, 1),
(6, 5, 2),
(7, 5, 3),
(12, 6, 1),
(13, 6, 2),
(14, 6, 3),
(15, 7, 1),
(17, 8, 2),
(16, 8, 4),
(19, 8, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materias`
--

CREATE TABLE `materias` (
  `idMateria` int(11) NOT NULL,
  `nombreMateria` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `materias`
--

INSERT INTO `materias` (`idMateria`, `nombreMateria`) VALUES
(1, 'INGLES'),
(2, 'MATEMATICAS'),
(3, 'SOCIALES'),
(4, 'CIENCIAS'),
(5, 'ECONOMIA'),
(6, 'PROGRAMACION');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notas`
--

CREATE TABLE `notas` (
  `idNota` int(11) NOT NULL,
  `idInscripcion` int(11) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `calificacion` decimal(4,2) NOT NULL,
  `fechaRegistro` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `notas`
--

INSERT INTO `notas` (`idNota`, `idInscripcion`, `descripcion`, `calificacion`, `fechaRegistro`) VALUES
(1, 1, 'DEBER', 9.00, '2025-07-19 02:45:21'),
(2, 3, 'Taller', 10.00, '2025-07-19 02:48:02'),
(3, 4, 'Deber', 9.00, '2025-07-19 02:48:17'),
(4, 5, 'DEBER', 9.00, '2025-07-19 03:09:17'),
(5, 8, 'DEBER', 8.00, '2025-07-19 03:09:36'),
(6, 12, 'TALLER 2', 10.00, '2025-07-20 03:21:45'),
(7, 12, 'EXAMEN', 9.00, '2025-07-20 03:21:59'),
(8, 6, 'EXAMEN', 10.00, '2025-07-21 00:57:21'),
(9, 8, 'EXAMEN', 10.00, '2025-07-24 00:48:44'),
(10, 5, 'EXAMEN', 10.00, '2025-07-27 19:34:50'),
(11, 12, 'DEBER1', 10.00, '2025-07-27 19:39:17');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `idUsuario` int(11) NOT NULL,
  `nombreUsuario` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`idUsuario`, `nombreUsuario`, `password`, `rol`) VALUES
(1, 'admin', 'admin123', 'Administrador'),
(2, 'profe', 'profe123', 'Profesor'),
(3, 'ANDREA', 'ANDRE12', 'Profesor'),
(4, 'JOSSELYN', '1234', 'Profesor');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `estudiantes`
--
ALTER TABLE `estudiantes`
  ADD PRIMARY KEY (`idEstudiante`),
  ADD UNIQUE KEY `cedula` (`cedula`);

--
-- Indices de la tabla `inscripciones`
--
ALTER TABLE `inscripciones`
  ADD PRIMARY KEY (`idInscripcion`),
  ADD UNIQUE KEY `idx_estudiante_materia` (`idEstudiante`,`idMateria`),
  ADD KEY `idMateria` (`idMateria`);

--
-- Indices de la tabla `materias`
--
ALTER TABLE `materias`
  ADD PRIMARY KEY (`idMateria`);

--
-- Indices de la tabla `notas`
--
ALTER TABLE `notas`
  ADD PRIMARY KEY (`idNota`),
  ADD KEY `idInscripcion` (`idInscripcion`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`idUsuario`),
  ADD UNIQUE KEY `nombreUsuario` (`nombreUsuario`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `estudiantes`
--
ALTER TABLE `estudiantes`
  MODIFY `idEstudiante` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `inscripciones`
--
ALTER TABLE `inscripciones`
  MODIFY `idInscripcion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `materias`
--
ALTER TABLE `materias`
  MODIFY `idMateria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `notas`
--
ALTER TABLE `notas`
  MODIFY `idNota` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `idUsuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `inscripciones`
--
ALTER TABLE `inscripciones`
  ADD CONSTRAINT `inscripciones_ibfk_1` FOREIGN KEY (`idEstudiante`) REFERENCES `estudiantes` (`idEstudiante`) ON DELETE CASCADE,
  ADD CONSTRAINT `inscripciones_ibfk_2` FOREIGN KEY (`idMateria`) REFERENCES `materias` (`idMateria`) ON DELETE CASCADE;

--
-- Filtros para la tabla `notas`
--
ALTER TABLE `notas`
  ADD CONSTRAINT `notas_ibfk_1` FOREIGN KEY (`idInscripcion`) REFERENCES `inscripciones` (`idInscripcion`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
