--
-- PostgreSQL database dump
--

-- Dumped from database version 10.0
-- Dumped by pg_dump version 10.1

-- Started on 2018-06-06 15:28:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2905 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 197 (class 1259 OID 36455)
-- Name: attribute; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE attribute (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    value character varying(255) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 196 (class 1259 OID 36453)
-- Name: attribute_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE attribute_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2906 (class 0 OID 0)
-- Dependencies: 196
-- Name: attribute_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE attribute_id_seq OWNED BY attribute.id;


--
-- TOC entry 205 (class 1259 OID 36490)
-- Name: brand; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE brand (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description text,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    image_id integer
);


--
-- TOC entry 204 (class 1259 OID 36488)
-- Name: brand_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE brand_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2907 (class 0 OID 0)
-- Dependencies: 204
-- Name: brand_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE brand_id_seq OWNED BY brand.id;


--
-- TOC entry 207 (class 1259 OID 36501)
-- Name: category; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE category (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description text,
    "position" integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    image_id integer
);


--
-- TOC entry 206 (class 1259 OID 36499)
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE category_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2908 (class 0 OID 0)
-- Dependencies: 206
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE category_id_seq OWNED BY category.id;


--
-- TOC entry 201 (class 1259 OID 36471)
-- Name: image; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE image (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    url character varying(255) NOT NULL,
    "position" integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 200 (class 1259 OID 36469)
-- Name: image_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE image_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2909 (class 0 OID 0)
-- Dependencies: 200
-- Name: image_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE image_id_seq OWNED BY image.id;


--
-- TOC entry 211 (class 1259 OID 36524)
-- Name: order_item; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE order_item (
    id integer NOT NULL,
    quantity integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    order_object_id integer NOT NULL,
    product_id integer NOT NULL
);


--
-- TOC entry 210 (class 1259 OID 36522)
-- Name: order_item_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE order_item_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2910 (class 0 OID 0)
-- Dependencies: 210
-- Name: order_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE order_item_id_seq OWNED BY order_item.id;


--
-- TOC entry 199 (class 1259 OID 36463)
-- Name: order_object; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE order_object (
    id integer NOT NULL,
    close boolean NOT NULL,
    creator_id integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 198 (class 1259 OID 36461)
-- Name: order_object_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE order_object_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2911 (class 0 OID 0)
-- Dependencies: 198
-- Name: order_object_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE order_object_id_seq OWNED BY order_object.id;


--
-- TOC entry 209 (class 1259 OID 36512)
-- Name: product; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE product (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    description text,
    external_link character varying(150) NOT NULL,
    visible boolean NOT NULL,
    "position" integer DEFAULT 0 NOT NULL,
    price numeric(12,2) NOT NULL,
    quantity_stock integer NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL,
    category_id integer NOT NULL,
    brand_id integer NOT NULL,
    version integer NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 36530)
-- Name: product_2_attribute; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE product_2_attribute (
    product_id integer NOT NULL,
    attribute_id integer NOT NULL
);


--
-- TOC entry 213 (class 1259 OID 36533)
-- Name: product_2_image; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE product_2_image (
    product_id integer NOT NULL,
    image_id integer NOT NULL
);


--
-- TOC entry 208 (class 1259 OID 36510)
-- Name: product_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE product_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2912 (class 0 OID 0)
-- Dependencies: 208
-- Name: product_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE product_id_seq OWNED BY product.id;


--
-- TOC entry 203 (class 1259 OID 36479)
-- Name: user_account; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_account (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    role character varying(20) NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone NOT NULL
);


--
-- TOC entry 202 (class 1259 OID 36477)
-- Name: user_account_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE user_account_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2913 (class 0 OID 0)
-- Dependencies: 202
-- Name: user_account_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE user_account_id_seq OWNED BY user_account.id;


--
-- TOC entry 2724 (class 2604 OID 36458)
-- Name: attribute id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY attribute ALTER COLUMN id SET DEFAULT nextval('attribute_id_seq'::regclass);


--
-- TOC entry 2728 (class 2604 OID 36493)
-- Name: brand id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY brand ALTER COLUMN id SET DEFAULT nextval('brand_id_seq'::regclass);


--
-- TOC entry 2729 (class 2604 OID 36504)
-- Name: category id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY category ALTER COLUMN id SET DEFAULT nextval('category_id_seq'::regclass);


--
-- TOC entry 2726 (class 2604 OID 36474)
-- Name: image id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY image ALTER COLUMN id SET DEFAULT nextval('image_id_seq'::regclass);


--
-- TOC entry 2732 (class 2604 OID 36527)
-- Name: order_item id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item ALTER COLUMN id SET DEFAULT nextval('order_item_id_seq'::regclass);


--
-- TOC entry 2725 (class 2604 OID 36466)
-- Name: order_object id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_object ALTER COLUMN id SET DEFAULT nextval('order_object_id_seq'::regclass);


--
-- TOC entry 2730 (class 2604 OID 36515)
-- Name: product id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY product ALTER COLUMN id SET DEFAULT nextval('product_id_seq'::regclass);


--
-- TOC entry 2727 (class 2604 OID 36482)
-- Name: user_account id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_account ALTER COLUMN id SET DEFAULT nextval('user_account_id_seq'::regclass);


--
-- TOC entry 2882 (class 0 OID 36455)
-- Dependencies: 197
-- Data for Name: attribute; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO attribute (id, name, value, created, updated) VALUES (1, 'Размер', '100 мм', '2018-05-19 15:44:08.939', '2018-05-19 15:44:08.939');
INSERT INTO attribute (id, name, value, created, updated) VALUES (2, 'Размер', '150 мм', '2018-05-19 15:44:29.902', '2018-05-19 15:44:29.902');
INSERT INTO attribute (id, name, value, created, updated) VALUES (3, 'Диаметр', '125 мм', '2018-05-19 15:44:50.609', '2018-05-19 15:44:50.609');


--
-- TOC entry 2890 (class 0 OID 36490)
-- Dependencies: 205
-- Data for Name: brand; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO brand (id, name, description, created, updated, image_id) VALUES (1, 'TOLSEN', '', '2018-05-19 15:47:35.633', '2018-05-19 15:47:35.633', 1);
INSERT INTO brand (id, name, description, created, updated, image_id) VALUES (2, 'Yato', '', '2018-05-19 15:48:01.282', '2018-05-19 15:48:01.282', 1);


--
-- TOC entry 2892 (class 0 OID 36501)
-- Dependencies: 207
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO category (id, name, description, "position", created, updated, image_id) VALUES (2, 'Плоскогубцы', '', 1, '2018-05-19 16:00:19.507', '2018-05-19 16:00:19.507', 2);
INSERT INTO category (id, name, description, "position", created, updated, image_id) VALUES (3, 'Отвертки', '', 2, '2018-05-19 16:06:15.233', '2018-05-19 16:06:15.233', 1);
INSERT INTO category (id, name, description, "position", created, updated, image_id) VALUES (4, 'Диски по дереву', '', 3, '2018-05-24 21:02:33.183', '2018-05-24 21:02:33.183', 1);
INSERT INTO category (id, name, description, "position", created, updated, image_id) VALUES (5, 'Измерительный инструмент', '', 4, '2018-05-31 10:46:05.962', '2018-05-31 10:46:05.962', 4);


--
-- TOC entry 2886 (class 0 OID 36471)
-- Dependencies: 201
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO image (id, name, url, "position", created, updated) VALUES (1, 'Отвертка', 'https://instrumental.by/files/products/yt-25926.220x220.jpg?f401e1ddc5edd3c6530f92389f6d4a18', 1, '2018-05-19 15:45:58.082', '2018-05-19 15:45:58.082');
INSERT INTO image (id, name, url, "position", created, updated) VALUES (2, 'Плоскогубцы', 'https://instrumental.by/files/products/10015_1.220x220.jpg?edcf103deddb39d58173957c6fa0467d', 2, '2018-05-19 15:46:57.333', '2018-05-19 15:46:57.333');
INSERT INTO image (id, name, url, "position", created, updated) VALUES (3, 'Диск по дереву', 'https://instrumental.by/files/products/pol_pl_Tarcza-do-drewna-250x32x40T-25--3765_2.220x220.jpg?7d42ab3d31f2f01edb15cc280156e050', 3, '2018-05-27 19:21:46.032', '2018-05-27 19:21:46.032');
INSERT INTO image (id, name, url, "position", created, updated) VALUES (4, 'Рулетка', 'https://instrumental.by/files/products/yt-71055.140x150.jpg?76b0369c3f3884f6fba1625d4a786815', 6, '2018-05-31 10:41:54.904', '2018-05-31 10:42:33.529');


--
-- TOC entry 2896 (class 0 OID 36524)
-- Dependencies: 211
-- Data for Name: order_item; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (7, 1, '2018-05-31 10:17:33.395', '2018-05-31 10:17:33.395', 9, 8);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (9, 2, '2018-05-31 10:19:48.582', '2018-05-31 10:19:48.582', 9, 8);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (10, 8, '2018-05-31 20:24:10.11', '2018-05-31 20:24:10.11', 9, 7);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (13, 1, '2018-06-02 21:06:08.579', '2018-06-02 21:06:08.579', 9, 6);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (15, 6, '2018-06-03 19:00:30.953', '2018-06-03 19:00:30.953', 9, 7);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (16, 1, '2018-06-03 19:31:15.094', '2018-06-03 19:31:15.094', 9, 10);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (17, 2, '2018-06-03 19:33:00.221', '2018-06-03 19:33:00.221', 9, 7);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (23, 2, '2018-06-03 20:37:46.888', '2018-06-03 20:37:46.888', 11, 10);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (25, 2, '2018-06-06 15:12:08.816', '2018-06-06 15:12:08.816', 12, 8);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (26, 1, '2018-06-06 15:12:16.983', '2018-06-06 15:12:16.983', 12, 10);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (27, 1, '2018-06-06 15:12:35.523', '2018-06-06 15:12:35.523', 12, 12);
INSERT INTO order_item (id, quantity, created, updated, order_object_id, product_id) VALUES (28, 1, '2018-06-06 15:12:43.904', '2018-06-06 15:12:43.904', 12, 13);


--
-- TOC entry 2884 (class 0 OID 36463)
-- Dependencies: 199
-- Data for Name: order_object; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO order_object (id, close, creator_id, created, updated) VALUES (11, false, 4, '2018-06-03 20:27:33.62', '2018-06-03 20:27:33.62');
INSERT INTO order_object (id, close, creator_id, created, updated) VALUES (9, true, 4, '2018-05-29 22:19:46.67', '2018-06-03 21:43:05.78');
INSERT INTO order_object (id, close, creator_id, created, updated) VALUES (12, false, 6, '2018-06-06 15:12:08.802', '2018-06-06 15:12:08.802');


--
-- TOC entry 2894 (class 0 OID 36512)
-- Dependencies: 209
-- Data for Name: product; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product (id, name, description, external_link, visible, "position", price, quantity_stock, created, updated, category_id, brand_id, version) VALUES (6, 'Отвертка крестовая РН 0x75мм Yato', 'Код товара: YT-25926', 'https://instrumental.by/products/Otvertka-krestovaia-RN-1x200mm', true, 1, 1.62, 5, '2018-05-24 20:58:49.563', '2018-05-24 20:58:49.563', 3, 2, 1);
INSERT INTO product (id, name, description, external_link, visible, "position", price, quantity_stock, created, updated, category_id, brand_id, version) VALUES (8, 'Диск пильный по дереву 250х32х40Т', '', 'https://instrumental.by/products/disk-pilnyj-po-derevu-250h32h40t', true, 3, 5.53, 10, '2018-05-27 19:24:17.221', '2018-05-27 19:24:17.221', 4, 2, 1);
INSERT INTO product (id, name, description, external_link, visible, "position", price, quantity_stock, created, updated, category_id, brand_id, version) VALUES (7, 'Плоскогубцы универсальные «EXTRA PROFI» 160мм', '', 'https://instrumental.by/products/ploskogubtsy-universalnye-extra-profi-160mm', true, 2, 4.92, 143, '2018-05-24 21:00:36.513', '2018-05-31 10:54:08.457', 2, 1, 2);
INSERT INTO product (id, name, description, external_link, visible, "position", price, quantity_stock, created, updated, category_id, brand_id, version) VALUES (11, 'Диск пильный с твердосплавными напайками 200x30x40', 'YT-60652', 'https://instrumental.by/products/disk-pilnyj-s-tverdosplavnymi-napajkami-200x30x40', true, 2, 8.43, 14, '2018-06-01 13:04:27.96', '2018-06-01 13:04:27.96', 4, 2, 1);
INSERT INTO product (id, name, description, external_link, visible, "position", price, quantity_stock, created, updated, category_id, brand_id, version) VALUES (10, 'Рулетка 3м*16мм', 'YT-71055', 'https://instrumental.by/products/ruletka-3m-16mm', true, 6, 2.19, 20, '2018-05-31 10:45:16.806', '2018-06-01 13:46:20.086', 5, 1, 2);
INSERT INTO product (id, name, description, external_link, visible, "position", price, quantity_stock, created, updated, category_id, brand_id, version) VALUES (12, 'Отвертка реверсивная с трещоткой «7 в 1»', 'T20033', 'https://instrumental.by/products/otvertka-reversivnaya-s-treschotkoj-i-naborom-bit-7-v-1', true, 3, 4.40, 20, '2018-06-06 14:54:38.755', '2018-06-06 14:54:38.755', 3, 1, 1);
INSERT INTO product (id, name, description, external_link, visible, "position", price, quantity_stock, created, updated, category_id, brand_id, version) VALUES (13, 'Набор отверток для точных работ 6пр.', 'Код товара: YT-25861', 'https://instrumental.by/products/nabor-otvertok-dlya-tochnyh-rabot-6pr', true, 3, 6.36, 10, '2018-06-06 14:56:43.619', '2018-06-06 14:56:43.619', 3, 2, 1);


--
-- TOC entry 2897 (class 0 OID 36530)
-- Dependencies: 212
-- Data for Name: product_2_attribute; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2898 (class 0 OID 36533)
-- Dependencies: 213
-- Data for Name: product_2_image; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO product_2_image (product_id, image_id) VALUES (6, 1);
INSERT INTO product_2_image (product_id, image_id) VALUES (7, 2);
INSERT INTO product_2_image (product_id, image_id) VALUES (8, 3);
INSERT INTO product_2_image (product_id, image_id) VALUES (10, 4);
INSERT INTO product_2_image (product_id, image_id) VALUES (11, 3);
INSERT INTO product_2_image (product_id, image_id) VALUES (12, 1);
INSERT INTO product_2_image (product_id, image_id) VALUES (13, 1);


--
-- TOC entry 2888 (class 0 OID 36479)
-- Dependencies: 203
-- Data for Name: user_account; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO user_account (id, name, email, password, enabled, role, created, updated) VALUES (1, 'Артем', 'said.kirill@gmail.com', '$2a$10$OJMybUpT41L4ABbrPAVEd.3hHEdehWtA3g91xDBkMOd5MvLOJccWq', true, 'admin', '2018-05-19 16:07:10.221', '2018-05-19 16:07:10.221');
INSERT INTO user_account (id, name, email, password, enabled, role, created, updated) VALUES (3, 'Артем Ивахненко', 'said-kirill@rambler.ru', '$2a$10$Tly1ImaJos0MbCS.DD9aiu6X6ay1nE0EBFmdg8WhZsGeZf1LqkRF2', true, 'admin', '2018-05-20 01:37:54.437', '2018-05-20 01:37:54.437');
INSERT INTO user_account (id, name, email, password, enabled, role, created, updated) VALUES (4, 'Петя', 'peter@gmail.com', '$2a$10$NuML2ODyCU77o4giM0bXl.n1zev6EnLFddgm7nFXlUM9Br1LIn2nm', true, 'customer', '2018-05-27 14:54:13.882', '2018-05-27 14:54:13.882');
INSERT INTO user_account (id, name, email, password, enabled, role, created, updated) VALUES (5, 'Оксана', 'oksana@gmail.com', '$2a$10$EX5ifSFcVRCIGo8lVp05.e9WwkD/1dMCWHHDbIPceCLevINKvQdVO', true, 'manager', '2018-06-01 11:47:09.955', '2018-06-01 11:47:09.955');
INSERT INTO user_account (id, name, email, password, enabled, role, created, updated) VALUES (6, 'Алена', 'alena@gmail.com', '$2a$10$T9j0aX3lBbIJo3rvbCE87uuy3FEaqPOsaKyEhf49/G0iZPFlCHj9.', true, 'customer', '2018-06-06 15:05:12.376', '2018-06-06 15:05:12.376');
INSERT INTO user_account (id, name, email, password, enabled, role, created, updated) VALUES (7, 'Ivan', 'ivan@gmail.com', '$2a$10$J/SeX8itO/COxhapCgkjxulb/1FaK9G9NO9mZ6FwSFRWSD7baF9Xe', false, 'customer', '2018-06-06 15:11:20.218', '2018-06-06 15:11:20.218');


--
-- TOC entry 2914 (class 0 OID 0)
-- Dependencies: 196
-- Name: attribute_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('attribute_id_seq', 3, true);


--
-- TOC entry 2915 (class 0 OID 0)
-- Dependencies: 204
-- Name: brand_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('brand_id_seq', 2, true);


--
-- TOC entry 2916 (class 0 OID 0)
-- Dependencies: 206
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('category_id_seq', 5, true);


--
-- TOC entry 2917 (class 0 OID 0)
-- Dependencies: 200
-- Name: image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('image_id_seq', 4, true);


--
-- TOC entry 2918 (class 0 OID 0)
-- Dependencies: 210
-- Name: order_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('order_item_id_seq', 28, true);


--
-- TOC entry 2919 (class 0 OID 0)
-- Dependencies: 198
-- Name: order_object_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('order_object_id_seq', 12, true);


--
-- TOC entry 2920 (class 0 OID 0)
-- Dependencies: 208
-- Name: product_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('product_id_seq', 13, true);


--
-- TOC entry 2921 (class 0 OID 0)
-- Dependencies: 202
-- Name: user_account_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('user_account_id_seq', 7, true);


--
-- TOC entry 2734 (class 2606 OID 36460)
-- Name: attribute attribute_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY attribute
    ADD CONSTRAINT attribute_pk PRIMARY KEY (id);


--
-- TOC entry 2742 (class 2606 OID 36498)
-- Name: brand brand_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY brand
    ADD CONSTRAINT brand_pk PRIMARY KEY (id);


--
-- TOC entry 2744 (class 2606 OID 36509)
-- Name: category category_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_pk PRIMARY KEY (id);


--
-- TOC entry 2738 (class 2606 OID 36476)
-- Name: image image_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY image
    ADD CONSTRAINT image_pk PRIMARY KEY (id);


--
-- TOC entry 2748 (class 2606 OID 36529)
-- Name: order_item order_item_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_pk PRIMARY KEY (id);


--
-- TOC entry 2736 (class 2606 OID 36468)
-- Name: order_object order_object_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_object
    ADD CONSTRAINT order_object_pk PRIMARY KEY (id);


--
-- TOC entry 2746 (class 2606 OID 36521)
-- Name: product product_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_pk PRIMARY KEY (id);


--
-- TOC entry 2740 (class 2606 OID 36487)
-- Name: user_account user_account_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_account
    ADD CONSTRAINT user_account_pk PRIMARY KEY (id);


--
-- TOC entry 2750 (class 2606 OID 36541)
-- Name: brand brand_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY brand
    ADD CONSTRAINT brand_fk0 FOREIGN KEY (image_id) REFERENCES image(id);


--
-- TOC entry 2751 (class 2606 OID 36546)
-- Name: category category_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY category
    ADD CONSTRAINT category_fk0 FOREIGN KEY (image_id) REFERENCES image(id);


--
-- TOC entry 2754 (class 2606 OID 36561)
-- Name: order_item order_item_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_fk0 FOREIGN KEY (order_object_id) REFERENCES order_object(id);


--
-- TOC entry 2755 (class 2606 OID 36566)
-- Name: order_item order_item_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_item
    ADD CONSTRAINT order_item_fk1 FOREIGN KEY (product_id) REFERENCES product(id);


--
-- TOC entry 2749 (class 2606 OID 36536)
-- Name: order_object order_object_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY order_object
    ADD CONSTRAINT order_object_fk0 FOREIGN KEY (creator_id) REFERENCES user_account(id);


--
-- TOC entry 2756 (class 2606 OID 36571)
-- Name: product_2_attribute product_2_attribute_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_2_attribute
    ADD CONSTRAINT product_2_attribute_fk0 FOREIGN KEY (product_id) REFERENCES product(id);


--
-- TOC entry 2757 (class 2606 OID 36576)
-- Name: product_2_attribute product_2_attribute_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_2_attribute
    ADD CONSTRAINT product_2_attribute_fk1 FOREIGN KEY (attribute_id) REFERENCES attribute(id);


--
-- TOC entry 2758 (class 2606 OID 36581)
-- Name: product_2_image product_2_image_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_2_image
    ADD CONSTRAINT product_2_image_fk0 FOREIGN KEY (product_id) REFERENCES product(id);


--
-- TOC entry 2759 (class 2606 OID 36586)
-- Name: product_2_image product_2_image_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product_2_image
    ADD CONSTRAINT product_2_image_fk1 FOREIGN KEY (image_id) REFERENCES image(id);


--
-- TOC entry 2752 (class 2606 OID 36551)
-- Name: product product_fk0; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_fk0 FOREIGN KEY (category_id) REFERENCES category(id);


--
-- TOC entry 2753 (class 2606 OID 36556)
-- Name: product product_fk1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY product
    ADD CONSTRAINT product_fk1 FOREIGN KEY (brand_id) REFERENCES brand(id);


-- Completed on 2018-06-06 15:28:52

--
-- PostgreSQL database dump complete
--

