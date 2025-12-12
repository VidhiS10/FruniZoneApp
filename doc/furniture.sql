-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 11, 2025 at 01:56 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `furniture`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_addresses`
--

CREATE TABLE `tbl_addresses` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  `house_no` varchar(255) DEFAULT NULL,
  `area` varchar(255) DEFAULT NULL,
  `landmark` varchar(255) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `state` varchar(100) DEFAULT NULL,
  `address_type` enum('Home','Work','Other') DEFAULT 'Home',
  `is_default` tinyint(1) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_addresses`
--

INSERT INTO `tbl_addresses` (`id`, `user_id`, `full_name`, `phone`, `pincode`, `house_no`, `area`, `landmark`, `city`, `state`, `address_type`, `is_default`, `created_at`) VALUES
(2, 19, 'Vidhi', '7979797979', '395002', '101, sargram apt.', 'kailash nagar', 'majura gate', 'Surat', 'Gujarat', 'Home', 0, '2025-11-24 19:29:49'),
(5, 19, 'Vidhi-tishya', '7984276862', '395009', '5 permji nagar society 1', 'gujarat gas circle', 'adajan', 'Ahmedabad', 'Gujarat', 'Other', 1, '2025-11-26 21:05:51'),
(8, 24, 'Sahil', '9898989898', '395008', '1101-D, jolly apt.', 'vesu', 'near vijay laxmi hall', 'Surat', 'Gujarat', 'Home', 1, '2025-11-26 22:27:17'),
(9, 24, 'Sahil', '9898989898', '380001', 'syug', 'dhshh', 'ghvv', 'Ahmedabad', 'Gujarat', 'Other', 0, '2025-11-26 22:27:35');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_admin`
--

CREATE TABLE `tbl_admin` (
  `admin_id` int(11) NOT NULL,
  `admin_name` varchar(100) NOT NULL,
  `admin_email` varchar(100) NOT NULL,
  `admin_mobile` varchar(100) NOT NULL,
  `admin_password` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_admin`
--

INSERT INTO `tbl_admin` (`admin_id`, `admin_name`, `admin_email`, `admin_mobile`, `admin_password`) VALUES
(1, 'sahil', 'sahildharani890@gmail.com', '3698511111', '1111');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_banner`
--

CREATE TABLE `tbl_banner` (
  `ban_id` int(11) NOT NULL,
  `ban_img` varchar(500) NOT NULL,
  `ban_title` varchar(500) NOT NULL,
  `status` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_banner`
--

INSERT INTO `tbl_banner` (`ban_id`, `ban_img`, `ban_title`, `status`) VALUES
(1, './images/slider.jpg', 'ggggg', 1),
(4, './images/slider3.jpg', 'aaaa', 1),
(8, './images/slider2.jpg', 'aaaa', 0),
(11, './images/bed_a.jpg', 'aaaa', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_cart`
--

CREATE TABLE `tbl_cart` (
  `cart_id` int(11) NOT NULL,
  `total_price` varchar(100) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `sub_cat_id` varchar(50) NOT NULL,
  `cart_status` varchar(100) NOT NULL,
  `payment_done` varchar(200) NOT NULL,
  `delivery_address` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_category`
--

CREATE TABLE `tbl_category` (
  `cat_id` int(11) NOT NULL,
  `cat_name` varchar(50) NOT NULL,
  `cat_pic1` varchar(500) NOT NULL,
  `cat_pic2` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_category`
--

INSERT INTO `tbl_category` (`cat_id`, `cat_name`, `cat_pic1`, `cat_pic2`) VALUES
(31, 'Beds', './images/bed_a.jpg', './images/bed_a.jpg'),
(32, 'Sofas', './images/sofa_a.jpg', './images/sofa_a.jpg'),
(33, 'Wardrobes', './images/wardrobe_a.webp', './images/wardrobe_a.webp'),
(34, 'Dinings', './images/dining_a.jpg', './images/dining_a.jpg'),
(35, 'Chair', './images/chair_a.jpg', './images/chair_a.jpg'),
(37, 'Study table', './images/Screenshot (11).png', './images/Screenshot (11).png');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_coupen`
--

CREATE TABLE `tbl_coupen` (
  `coupen_id` int(11) NOT NULL,
  `coupen_title` varchar(200) NOT NULL,
  `coupen_code` varchar(200) NOT NULL,
  `coupen_description` varchar(500) NOT NULL,
  `coupen_img` varchar(200) NOT NULL,
  `coupen_discount` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_coupen`
--

INSERT INTO `tbl_coupen` (`coupen_id`, `coupen_title`, `coupen_code`, `coupen_description`, `coupen_img`, `coupen_discount`) VALUES
(9, 'First Order Offer', 'flat 20%', 'On any Product', './images/coupen.png', '20'),
(12, 'Weekly Deal', 'flat 50%', 'On any Product', './images/coupon1.jpg', '50');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_order`
--

CREATE TABLE `tbl_order` (
  `id` int(11) NOT NULL,
  `uid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `pname` varchar(500) NOT NULL,
  `ppic` varchar(500) NOT NULL,
  `date` varchar(300) NOT NULL,
  `time` varchar(300) NOT NULL,
  `amount` decimal(10,0) NOT NULL,
  `total_amount` decimal(10,0) NOT NULL,
  `quantity` int(11) DEFAULT 1,
  `status` int(11) NOT NULL DEFAULT 0,
  `is_wishlist` tinyint(1) NOT NULL DEFAULT 0,
  `state` int(11) NOT NULL DEFAULT 0,
  `address` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_order`
--

INSERT INTO `tbl_order` (`id`, `uid`, `pid`, `pname`, `ppic`, `date`, `time`, `amount`, `total_amount`, `quantity`, `status`, `is_wishlist`, `state`, `address`) VALUES
(50, 22, 12, 'Helios Amberly Sigma King Bed with Box Storage', './images/bed_b_1.jpg', '2025-11-22', '20:42:12.738862', 27997, 27997, 1, 1, 0, 5, 'ssdfd3555'),
(52, 22, 11, 'Helios Lewis Bronx King Bed with Box Storage', './images/bed_a_1.jpg', '2025-11-22', '20:58:07.089555', 21999, 21999, 1, 4, 0, 2, '1114'),
(53, 22, 22, 'Helios Reynan 2-Door Wardrobe', './images/wardrobe_a_1.jpg', '2025-11-23', '21:47:31.018069', 14499, 20187, 1, 4, 0, 1, '101 sargam aptkailash nagar majura gate395002'),
(54, 22, 17, 'Emily Fabric 3-Seater Sofa', './images/sofa_a_1.jpg', '2025-11-24', '00:22:09.021995', 17999, 17999, 1, 0, 0, 0, ''),
(55, 19, 22, 'Helios Reynan 2-Door Wardrobe', './images/wardrobe_a_1.jpg', '2025-11-25', '15:11:08.024203', 14499, 14499, 1, 1, 0, 0, 'Vidhi-tishya\n7984276862\n5 permji nagar society 1, gujarat gas circle, adajan, Ahmedabad, Gujarat - 3'),
(57, 19, 22, 'Helios Reynan 2-Door Wardrobe', '/images/wardrobe_a_1.jpg', '2025-11-23', '21:47:31.018069', 14499, 20187, 1, 1, 0, 5, '101 sargam apt kailash nagar majura gate395002'),
(58, 19, 12, 'Helios Amberly Sigma King Bed with Box Storage', './images/bed_b_1.jpg', '2025-11-26', '11:39:20.378985', 27997, 27997, 1, 1, 0, 0, 'Vidhi-tishya\n7984276862\n5 permji nagar society 1, gujarat gas circle, adajan, Ahmedabad, Gujarat - 3'),
(61, 24, 15, 'Helios Nebula Axion Metal Bunk Bed', './images/bed_d_1.jpg', '2025-11-27', '03:55:48.097446', 13999, 13999, 1, 1, 0, 0, 'Sahil\n9898989898\n1101-D, jolly apt., vesu, near vijay laxmi hall, Surat, Gujarat - 395008'),
(62, 19, 29, 'Cornell 4-Seater Dining Set with Chairs and Bench', './images/dining_c_1.jpg', '2025-11-27', '19:55:22.598865', 19797, 19797, 1, 1, 0, 1, 'Vidhi-tishya\n7984276862\n5 permji nagar society 1, gujarat gas circle, adajan, Ahmedabad, Gujarat - 3'),
(63, 19, 20, 'Cady Fabric 3-Seater Sofa ', './images/sofa_d_1.jpg', '2025-11-27', '20:14:12.182225', 25498, 25498, 1, 1, 0, 2, 'Vidhi-tishya\n7984276862\n5 permji nagar society 1, gujarat gas circle, adajan, Ahmedabad, Gujarat - 3'),
(64, 19, 13, 'Santorini Sandy Queen Bed with Box Storage', './images/bed_c_1.jpg', '2025-11-27', '20:38:35.463299', 29998, 29998, 1, 0, 0, 0, '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_review`
--

CREATE TABLE `tbl_review` (
  `review_id` int(11) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `sub_cat_id` varchar(50) NOT NULL,
  `review_title` varchar(300) NOT NULL,
  `review_description` varchar(500) NOT NULL,
  `review_star` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_sub_category`
--

CREATE TABLE `tbl_sub_category` (
  `sub_cat_id` int(11) NOT NULL,
  `sub_cat_name` varchar(100) NOT NULL,
  `sub_cat_color` varchar(200) NOT NULL,
  `sub_cat_price` varchar(100) NOT NULL,
  `sub_cat_discount` varchar(100) NOT NULL,
  `sub_cat_description` varchar(500) NOT NULL,
  `sub_cat_dimention` varchar(300) NOT NULL,
  `sub_cat_weight` varchar(100) NOT NULL,
  `sub_cat_primary_material` varchar(500) NOT NULL,
  `sub_cat_warenty` varchar(100) NOT NULL,
  `sub_cat_product_rating` varchar(100) NOT NULL,
  `sub_cat_sku` varchar(100) NOT NULL,
  `sub_cat_specification` varchar(100) NOT NULL,
  `sub_cat_pic1` varchar(200) NOT NULL,
  `sub_cat_pic2` varchar(200) NOT NULL,
  `sub_cat_pic3` varchar(200) NOT NULL,
  `sub_cat_pic4` varchar(200) NOT NULL,
  `cat_id` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_sub_category`
--

INSERT INTO `tbl_sub_category` (`sub_cat_id`, `sub_cat_name`, `sub_cat_color`, `sub_cat_price`, `sub_cat_discount`, `sub_cat_description`, `sub_cat_dimention`, `sub_cat_weight`, `sub_cat_primary_material`, `sub_cat_warenty`, `sub_cat_product_rating`, `sub_cat_sku`, `sub_cat_specification`, `sub_cat_pic1`, `sub_cat_pic2`, `sub_cat_pic3`, `sub_cat_pic4`, `cat_id`) VALUES
(11, 'Helios Lewis Bronx King Bed with Box Storage', 'Brown', '21999', '40', 'This bed with storage is compact and tidy, made of engineering wood, it comes with 5 compartments of storage units with 2 drawers in the bed side and 3 open shelf storage in the front face of the bed cot. Drawers are utilized to store the pillows, mattress protectors etc. The bed cot is coated with melamine for scratch resistance, the metal support is rigid to sustain the man power load on the cot.', '1.85 m x 2 m x 0.4 m', '200', 'Engineered Wood', '1year', '5star', 'Kings Bed', 'Bed', './images/bed_a_1.jpg', './images/bed_a_2.jpg', './images/bed_a_3.jpg', './images/bed_a_4.jpg', '31'),
(12, 'Helios Amberly Sigma King Bed with Box Storage', 'Brown', '27997', '40', 'Rooted in nature and comfort, this king-sized bed is all about rustic minimalism and clean, soft forms. Made with prelaminated engineered wood with both surfaces by synthetic resin-impregnated base papers under the influence of heat and pressure, which gives a nice even texture and wood grain or solid matt colour. This is a cost-effective finish compared to melamine-facing. The fibreboards compressed with hardwood extracts possess stronger cores with strong screw-holding capacity and are chemica', '195 cm x 215 cm x 110 cm', '215', 'Engineered Wood', '1year', '5star', 'Queen Bed', 'Bed', './images/bed_b_1.jpg', './images/bed_b_2.jpg', './images/bed_b_3.jpg', './images/bed_b_4.jpg', '31'),
(13, 'Santorini Sandy Queen Bed with Box Storage', 'White', '29998', '40', 'Convert your bedroom space into a comfortable abode with this queen-sized bed. It is made of prelaminated engineered wood with a distressed finish for an aesthetically pleasing look. The tall headboard features contrasting wooden panels and top for a striking look. This bed comes with box storage with multiple divisions to store your extra bedding accessories.', '201.2 cm x 171.2 cm x 110 cm', '171.2', 'Engineered Wood', '1year', '5star', 'Queen Bed', 'Bed', './images/bed_c_1.jpg', './images/bed_c_2.jpg', './images/bed_c_3.jpg', './images/bed_c_4.jpg', '31'),
(15, 'Helios Nebula Axion Metal Bunk Bed', 'Multicolour', '13999', '50', 'Give your kids’ bedroom a vibrant touch with this multitone bunk bed. It boasts a mild steel frame that is powder coated for improved durability. It features a single bed at the bottom and the top that is accessible with the built-in ladder. The bunk design is space-saving, and the side railing on the top ensures safety for your little ones.', '95.5 x 205 x 172cm', '205', 'Metal', '1year', '5star', 'Bunk Bed', 'Bed', './images/bed_d_1.jpg', './images/bed_d_2.jpg', './images/bed_d_3.jpg', './images/bed_d_4.jpg', '31'),
(16, 'Helios Arvis Diwan Bed ', 'Wenge', '14499', '40', 'Diwan storage beds are compact and tidy, with their design intended to only be as wide as the mattress they support and, it a great space saver for smaller rooms.', '1.88 m x 0.81 m x 0.37 m', '81', 'Melamine', '1year', '5star', 'Day Bed', 'Bed', './images/bed_e_1.jpg', './images/bed_e_2.jpg', './images/bed_e_3.jpg', './images/bed_e_4.jpg', '31'),
(17, 'Emily Fabric 3-Seater Sofa', 'Brown', '17999', '50', 'Enjoy complete relaxation in this comfy three-seater sofa crafted from high-quality Pinewood. It is strong, lightweight, and offers comfortable seating. The sofa is upholstered with durable and recyclable polyester fabric and designed with high elastic nylon webbing. Filled with high-resilient foam, this sofa comes with a supportive backrest packed with Non-Siliconised polyfill for greater back support and comfort. It comes with ABS legs for longer durability', '184 cm x 92 cm x 88 cm', '92', 'Fabric', '1year', '5star', 'Sofa', 'Sofa', './images/sofa_a_1.jpg', './images/sofa_a_2.jpg', './images/sofa_a_3.jpg', './images/sofa_a_4.jpg', '32'),
(18, 'Helios Clary Nxt Fabric 3+2 Seater Sofa Set', 'Beige', '27998', '50', 'Modern looking sofa with timeless look and very affordable for first time buyers. Upholstered with high quality polyester fabric which ensures durability. Good quality Pu foam with elastic webbing in the sofa seat and neat construction of the backrest with polyfill - cushioning ensures comfort in seating for long hours. PVC legs underneath add to the elegant look of the Sofa. Material Brief: Frame is made from Solid wood - Pinewood with a certified acceptable limit of moisture to prevent warping', ' 3-Seater: 173 cm x 80 cm x 83 cm, 2-Seater: 122 cm x 80 cm x 83 cm', '80', 'Fabric', '1year', '5star', '3+2 Seater Sofa Set', 'Sofa', './images/sofa_b_1.jpg', './images/sofa_b_2.jpg', './images/sofa_b_3.jpg', './images/sofa_b_4.jpg', '32'),
(19, 'Walter Half Leather 2-Seater Sofa', 'Brown', '29999', '50', 'Invite plush comfort into your living space with this 2-seater sofa. Showcasing a pinewood construction, this two-seater comes with track arms and exposed legs. The half-leather upholstery is a mix of plush buffalo leather and faux leather to ensure durability and lend a luxe touch.', '147 cm x 95 cm x 93 cm', '95', 'Leather', '6month', '5star', 'Sofa', 'Sofa', './images/sofa_c_1.jpg', './images/sofa_c_2.jpg', './images/sofa_c_3.jpg', './images/sofa_c_4.jpg', '32'),
(20, 'Cady Fabric 3-Seater Sofa ', 'Brown', '25498', '50', 'Enjoy versatility, convenience, and comfort when you bring this sofa bed home. It boasts a pine wood frame with plastic legs. This sofa is upholstered in polyester fabric for durability and elegance. The click-clack mechanism lets you adjust the sofa in 3 positions – slightly reclined, upright, and flat, allowing you to use it as a sofa, lounger, and sleeper bed. The split back lets you recline one side, while the other remains upright.', '175 cm x 97 cm x 80 cm', '97', 'Fabric', '6month', '5star', 'Sofa', 'Sofa', './images/sofa_d_1.jpg', './images/sofa_d_2.jpg', './images/sofa_d_3.jpg', './images/sofa_d_4.jpg', '32'),
(21, 'Mojo Velvet 1-Seater Sofa', 'Blue', '16999', '50', 'Add a comfortable nook in your living space with this 1-seater sofa. Constructed from pinewood, this sofa is durable and boasts a high-tensile strength. It features a plush cushioned seat, a well-padded back, and pillow-top cushioned arms that allow you to lounge in style. The velvet fabric upholstery lends a luxe touch to this 1-seater.', '92 cm x 94 cm x 96 cm', '94', 'Velvet', '6month', '5star', 'Sofa', 'Sofa', './images/sofa_e_1.jpg', './images/sofa_e_2.jpg', './images/sofa_e_3.jpg', './images/sofa_e_4.jpg', '32'),
(22, 'Helios Reynan 2-Door Wardrobe', 'White', '14499', '50', 'This two-door wardrobe is made by compressing wood chips with glue. In flat-pressed particle board, the chips are mainly parallel to the surface. The chips in the surface layer are thinner than those in the middle layer, so the surface of the particle board is denser. It is resistance to termite and wood borer. Door/ Drawer fronts : Particle board is made by compressing wood chips with glue. In flat-pressed particle board, the chips are mainly parallel to the surface.', ' 182 cm x 47 cm x 182 cm', '47', 'Engineered Wood', '1year', '5star', 'Wardrobe', 'Wardrobe', './images/wardrobe_a_1.jpg', './images/wardrobe_a_2.jpg', './images/wardrobe_a_3.jpg', './images/wardrobe_a_4.jpg', '33'),
(23, 'Quadro Promo 4-Door Wardrobe with Mirror', 'Brown', '27999', '50', 'An important storage solution for the bedroom, this classic four-door wardrobe has been crafted from particle board to store your essentials with ease while saving space. It contains eight fixed shelves and one pull-out drawer that allow effortless organization along with a contemporary melamine finish and a mirror at the front door that uplifts the overall look. The wardrobe has a unique set of lock and keys for improved security and a slide in back panel for improved stability.', ' 1.60 m L x 0.47 m W x 1.82 m H', '47', 'Engineered Wood', '6month', '5star', 'Wardrobe', 'Wardrobe', './images/wardrobe_b_1.jpg', './images/wardrobe_b_2.jpg', './images/wardrobe_b_3.jpg', './images/wardrobe_b_4.jpg', '33'),
(24, 'Helios Lawrence 3-Door Wardrobe with Mirror', 'Beige', '22999', '50', 'This 3-door wardrobe inside design is made as per Indian standard interior. The design features multiple storage spaces to meet your every need. It features a rod to hang your blazers, coats, and shirts. This wardrobe design also has a drawer with a lock to provide you with even more storage space to help keep you organized. A full-length mirror is fixed on the exterior door. Made with engineered wood with a smooth surface with no grain makes it easier to work and paint. It is resistant to termi', '121 x 47 x 185cm', '47', 'Engineered Wood', '6month', '5star', 'Wardrobe', 'Wardrobe', './images/wardrobe_c_1.jpg', './images/wardrobe_c_2.jpg', './images/wardrobe_c_3.jpg', './images/wardrobe_c_4.jpg', '33'),
(25, 'Helios Rhine 4-Door Wardrobe with Mirror', 'Wenge', '27999', '50', 'A 4 Door wardrobe that is elegant and fits right into your house furniture collection. This piece is elegant, stylish and features an intricate design.', ' 1.60 m x 47 cm x 1.82 m', '47', 'Engineered Wood', '6month', '5star', 'Wardrobe', 'Wardrobe', './images/wardrobe_d_1.jpg', './images/wardrobe_d_2.jpg', './images/wardrobe_d_3.jpg', './images/wardrobe_d_4.jpg', '33'),
(26, 'Leon 2-Door Wardrobe', 'Brown', '22999', '50', 'The primary feature of this furniture is its preliminary engineered wood construction, comprising fiber boards compressed with hardwood extracts, resulting in a robust core with excellent screw-holding capacity. Additionally, the wood is chemically treated to protect against termites and borer infestation, ensuring long-lasting durability. The secondary feature is the prelam paperfoil finish, wherein the engineered wood is laminated on both surfaces using synthetic resin-impregnated base papers ', '80 cm x 58 cm x 210 cm', '58', 'Engineered Wood', '6month', '5star', 'Wardrobe', 'Wardrobe', './images/wardrobe_e_1.jpg', './images/wardrobe_e_2.jpg', './images/wardrobe_e_3.jpg', './images/wardrobe_e_4.jpg', '33'),
(27, 'Nirvana Atharva Sheesham Wood 6-Seater Dining Set with Indus Chairs', 'Brown and Green', '26995', '50', 'Make your dining space look elegant by adding this dining set. This set comes with a dining table and six dining chairs. This dining table top is designed from sheesham wood and the veneer finish adds extra realm to the table. The table legs are made of high gauge, non-magnetic mild steel that provides enhanced durability and excellent load-bearing capacity. The metal components are pretreated through a phosphating process to avoid rusting over a period of time. The legs are sprayed with electri', 'Dining Table : 175 cm x 90 cm x 78.5 cm, Dining Chair : 47.2 cm x 45 cm x 95 cm', '90', 'Sheesham Wood', '6month', '5star', 'Dining', 'Dining', './images/dining_a_1.jpg', './images/dining_a_2.jpg', './images/dining_a_3.jpg', './images/dining_a_4.jpg', '34'),
(28, 'Helios Lucia 6-Seater Dining Set with Chairs and Bench', 'White', '12999', '30', 'Make your dining space look aesthetic by adding this dining set. This set comes with a dining table, a dining bench, and four dining chairs. Made with engineered wood panels to give the natural wooden look while mild steel provides higher gauge, non-magnetic, excellent durability, and load bearing strength. Electrically charged powder particles are sprayed on metals such as MS and aluminium using a spray gun for supreme adhesion which offers high resistance to scratches, chips off, dents, moistu', 'Dining Table: 160 cm x 80 cm x 75 cm, Dining Chair: 47.6 cm x 40 cm x 86 cm, Dining Bench: 120 cm x 35 cm x 45 cm', '80', 'Engineered Wood', '6month', '5star', 'Dining', 'Dining', './images/dining_b_1.jpg', './images/dining_b_2.jpg', './images/dining_b_3.jpg', './images/dining_b_4.jpg', '34'),
(29, 'Cornell 4-Seater Dining Set with Chairs and Bench', 'Brown', '19797', '30', 'It is engineered wood with smooth surface with no grain makes it easier to work and paint. It is resistance to termite and wood borer.', '4 Seater Table : 1.2 m x 0.75 m x 0.74 m, Dining Chair : 45.5 cm x 48.5 cm x 90 cm, Dining Small Bench : 90 cm x 40 cm x 48.5 cm', '75', 'Engineered Wood', '1year', '5star', 'Dining', 'Dining', './images/dining_c_1.jpg', './images/dining_c_2.jpg', './images/dining_c_3.jpg', './images/dining_c_4.jpg', '34'),
(30, 'Nirvana Kaya 6-Seater Dining Set with Indus Chairs', 'Brown and Beige', '28995', '25', 'Make your dining space look elegant by adding this dining set. This set comes with a dining table and six dining chairs. This dining table is designed from engineered wood with marquetry art on the top to add an old-world charm. The veneer finish adds extra realm to the table. The table legs are made of high gauge, non-magnetic mild steel that provides enhanced durability and excellent load-bearing capacity. The metal components are pretreated through a phosphating process to avoid rusting over ', '6-Seater Dining Table : 175 cm x 90 cm x 78.5 cm, Dining Chair : 47.2 cm x 45 cm x 95 cm', '90', 'Engineered Wood', '1year', '5star', 'Dining', 'Dining', './images/dining_d_1.jpg', './images/dining_d_2.jpg', './images/dining_d_3.jpg', './images/dining_d_4.jpg', '34'),
(31, 'Diana Brown Beech Wood 6-Seater Dining Table With 2 Chairs And 2 Benches', 'Brown', '21996', '30', 'Made of Beech wood which is very hard and tough. It is also strong, with a close straight grain. Beech polishes well and withstands shocks and wear. Legs are made of 2pcs of beech wood sandwiched together which is very hard and tough. It is also strong, with a close straight grain. Beech polishes well and withstands shocks and wear. Natural wood Veneer is pasted on the MDF parts to have a natural appearance of solid wood for extendable durability', ' Dining Table: 1.54 m x 0.74 m x 0.90 m, chair: 51.5 cm x 88 cm x 45 cm, Bench: 1.20 m x 0.38 m x 0.48 m', '74', 'Engineered Wood', '1year', '5star', 'Dining', 'Dining', './images/dining_e_1.jpg', './images/dining_e_2.jpg', './images/dining_e_3.jpg', './images/dining_e_4.jpeg', '34'),
(32, 'chair', 'Brown', '1000', '10', 'chair', '12', '15', 'Engineered Wood', '1year', '5star', 'chair', 'Bed', './images/bed_a.jpg', './images/bed_a.jpg', './images/bed_a.jpg', './images/bed_a.jpg', '35'),
(33, 'chair', 'Brown', '1000', '10', 'Bed', '12', '15', 'wood', '1year', '5star', 'Wardrobe', 'Sofa', './images/bed_a.jpg', './images/bed_a.jpg', './images/bed_a.jpg', './images/bed_a.jpg', '35'),
(34, 'short', 'black', '1000', '10', 'njn', '10', '1.5', 'wood', '1year', '4.3', 'dfdg', 'gsdg', './images/Screenshot (11).png', './images/Screenshot (19).png', './images/Screenshot (20).png', './images/Screenshot (17).png', '37');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `user_id` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `user_email` varchar(100) NOT NULL,
  `user_password` varchar(50) NOT NULL,
  `user_phone` bigint(20) NOT NULL,
  `user_pic` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`user_id`, `user_name`, `user_email`, `user_password`, `user_phone`, `user_pic`) VALUES
(19, 'Vidhi', 'v@gmail.com', '0da31305425d8647cddb7685b8cdb838', 7979797979, ''),
(22, 'anita', 'anita@gmail.com', '098fb561add82a8bd20ef0869dd5c7cf', 9797979797, ''),
(23, 'kishan', 'kishan@gmail.com', '328797d645a15f3b8a3050a45b54967b', 7878787878, ''),
(24, 'Sahil', 'sahil@gmail.com', 'e8c8f45019430b6f79862746e96d6e70', 9898989898, '');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_wishlist`
--

CREATE TABLE `tbl_wishlist` (
  `wish_id` int(11) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `sub_cat_id` varchar(50) NOT NULL,
  `wish_active` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_addresses`
--
ALTER TABLE `tbl_addresses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `tbl_admin`
--
ALTER TABLE `tbl_admin`
  ADD PRIMARY KEY (`admin_id`);

--
-- Indexes for table `tbl_banner`
--
ALTER TABLE `tbl_banner`
  ADD PRIMARY KEY (`ban_id`);

--
-- Indexes for table `tbl_cart`
--
ALTER TABLE `tbl_cart`
  ADD PRIMARY KEY (`cart_id`);

--
-- Indexes for table `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD PRIMARY KEY (`cat_id`);

--
-- Indexes for table `tbl_coupen`
--
ALTER TABLE `tbl_coupen`
  ADD PRIMARY KEY (`coupen_id`);

--
-- Indexes for table `tbl_order`
--
ALTER TABLE `tbl_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_review`
--
ALTER TABLE `tbl_review`
  ADD PRIMARY KEY (`review_id`);

--
-- Indexes for table `tbl_sub_category`
--
ALTER TABLE `tbl_sub_category`
  ADD PRIMARY KEY (`sub_cat_id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `UniqueKeyEmail` (`user_email`),
  ADD UNIQUE KEY `UniqueKeyPhoneNo` (`user_phone`);

--
-- Indexes for table `tbl_wishlist`
--
ALTER TABLE `tbl_wishlist`
  ADD PRIMARY KEY (`wish_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_addresses`
--
ALTER TABLE `tbl_addresses`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tbl_admin`
--
ALTER TABLE `tbl_admin`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_banner`
--
ALTER TABLE `tbl_banner`
  MODIFY `ban_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tbl_cart`
--
ALTER TABLE `tbl_cart`
  MODIFY `cart_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_category`
--
ALTER TABLE `tbl_category`
  MODIFY `cat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `tbl_coupen`
--
ALTER TABLE `tbl_coupen`
  MODIFY `coupen_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tbl_order`
--
ALTER TABLE `tbl_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=65;

--
-- AUTO_INCREMENT for table `tbl_review`
--
ALTER TABLE `tbl_review`
  MODIFY `review_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_sub_category`
--
ALTER TABLE `tbl_sub_category`
  MODIFY `sub_cat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `tbl_wishlist`
--
ALTER TABLE `tbl_wishlist`
  MODIFY `wish_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_addresses`
--
ALTER TABLE `tbl_addresses`
  ADD CONSTRAINT `tbl_addresses_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `tbl_user` (`user_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
