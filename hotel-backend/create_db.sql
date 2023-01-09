--
-- PostgreSQL database dump
--

-- Dumped from database version 14.3
-- Dumped by pg_dump version 14.3

-- Started on 2023-01-06 16:24:15

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 5 (class 2615 OID 16425)
-- Name: hotelDB; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA "hotelDB";


ALTER SCHEMA "hotelDB" OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 216 (class 1259 OID 24675)
-- Name: booked_days; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.booked_days (
    id bigint NOT NULL,
    booked_day date NOT NULL,
    room_id bigint NOT NULL
);


ALTER TABLE public.booked_days OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 24685)
-- Name: booked_days_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.booked_days ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.booked_days_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 221 (class 1259 OID 24705)
-- Name: payments; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.payments (
    id bigint NOT NULL,
    payment_method character varying NOT NULL,
    payment_status character varying NOT NULL
);


ALTER TABLE public.payments OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 24704)
-- Name: payments_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.payments ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.payments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 211 (class 1259 OID 16431)
-- Name: photos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.photos (
    id bigint NOT NULL,
    file_name character varying(255) NOT NULL,
    room_type_id bigint NOT NULL,
    file_path character varying(255) NOT NULL
);


ALTER TABLE public.photos OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16500)
-- Name: photos_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.photos ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.photos_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 219 (class 1259 OID 24697)
-- Name: reservations; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reservations (
    id bigint NOT NULL,
    customer_id character varying NOT NULL,
    date_from date NOT NULL,
    date_to date NOT NULL,
    room_id bigint NOT NULL,
    total_value double precision NOT NULL,
    payment_id bigint NOT NULL
);


ALTER TABLE public.reservations OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 24696)
-- Name: reservations_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.reservations ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservations_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 213 (class 1259 OID 16469)
-- Name: room_types; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.room_types (
    id bigint NOT NULL,
    room_type character varying(50) NOT NULL,
    description character varying(255),
    number_of_people integer DEFAULT 0 NOT NULL,
    price double precision DEFAULT 0 NOT NULL
);


ALTER TABLE public.room_types OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16468)
-- Name: room_types_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.room_types ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_types_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 210 (class 1259 OID 16426)
-- Name: rooms; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.rooms (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255),
    room_type_id bigint NOT NULL
);


ALTER TABLE public.rooms OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16481)
-- Name: rooms_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

ALTER TABLE public.rooms ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.rooms_id_seq
    START WITH 6
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- TOC entry 3356 (class 0 OID 24675)
-- Dependencies: 216
-- Data for Name: booked_days; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.booked_days (id, booked_day, room_id) FROM stdin;
19	2022-08-08	1
20	2022-08-09	1
21	2022-08-06	1
22	2022-08-07	1
24	2022-08-05	4
26	2022-08-04	4
29	2022-08-31	1
31	2022-09-01	1
32	2022-08-24	4
33	2022-08-25	4
34	2022-08-26	4
35	2022-08-27	4
36	2022-08-28	4
37	2022-08-29	4
38	2022-08-30	4
39	2022-08-18	2
40	2022-08-19	2
41	2022-08-20	2
49	2022-09-02	1
50	2022-09-03	1
51	2022-09-01	5
52	2022-09-02	5
53	2022-09-03	5
54	2022-09-01	3
57	2022-09-02	3
58	2022-09-08	3
60	2022-09-19	2
61	2022-09-20	2
62	2022-09-29	2
63	2022-09-30	1
64	2022-10-01	1
65	2022-09-30	2
66	2022-10-01	2
67	2022-10-02	2
68	2022-09-26	1
69	2022-09-27	1
70	2022-09-22	2
71	2022-09-23	2
72	2022-09-24	2
73	2022-09-25	2
74	2022-09-26	2
75	2022-09-27	2
76	2022-09-28	2
77	2022-10-04	2
78	2022-10-05	2
79	2022-10-03	1
80	2022-10-03	2
81	2022-10-05	1
82	2022-10-06	1
83	2022-10-07	1
84	2022-10-08	1
85	2022-10-09	1
86	2022-10-10	1
87	2022-10-11	1
88	2022-10-12	1
89	2022-10-13	1
91	2022-10-04	1
98	2022-10-15	1
102	2022-10-09	3
103	2022-10-10	3
105	2022-10-11	3
106	2022-10-12	3
107	2022-10-13	3
108	2022-10-14	3
109	2022-10-15	3
111	2022-11-01	1
112	2022-11-02	1
113	2022-11-03	1
114	2022-11-04	1
115	2022-11-05	1
116	2022-11-01	5
117	2022-11-02	5
118	2022-11-03	5
119	2022-11-04	5
120	2022-11-05	5
121	2022-12-01	5
122	2022-12-02	5
123	2022-12-03	5
124	2022-12-19	3
125	2022-12-20	3
126	2022-12-21	3
127	2022-12-22	3
\.


--
-- TOC entry 3361 (class 0 OID 24705)
-- Dependencies: 221
-- Data for Name: payments; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.payments (id, payment_method, payment_status) FROM stdin;
2	Gotówka	Nieopłacona
3	Gotówka	Nieopłacona
4	Gotówka	Nieopłacona
5	Gotówka	Nieopłacona
6	Karta kredytowa płatność przy zameldowaniu	Nieopłacona
7	Gotówka	Nieopłacona
\.


--
-- TOC entry 3351 (class 0 OID 16431)
-- Dependencies: 211
-- Data for Name: photos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.photos (id, file_name, room_type_id, file_path) FROM stdin;
14	3.1.jpg	2	https://hotel-management-s3-bucket.s3.eu-central-1.amazonaws.com/2/3.1.jpg
15	IMG_20210605_174103.jpg	4	https://hotel-management-s3-bucket.s3.eu-central-1.amazonaws.com/4/IMG_20210605_174103.jpg
23	4.1.jpg	1	https://hotel-management-s3-bucket.s3.eu-central-1.amazonaws.com/1/4.1.jpg
25	IMG_20200611_163552.jpg	4	https://hotel-management-s3-bucket.s3.eu-central-1.amazonaws.com/4/IMG_20200611_163552.jpg
27	1.2.jpg	3	https://hotel-management-s3-bucket.s3.eu-central-1.amazonaws.com/3/1.2.jpg
28	IMG_20210605_174042.jpg	3	https://hotel-management-s3-bucket.s3.eu-central-1.amazonaws.com/3/IMG_20210605_174042.jpg
\.


--
-- TOC entry 3359 (class 0 OID 24697)
-- Dependencies: 219
-- Data for Name: reservations; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reservations (id, customer_id, date_from, date_to, room_id, total_value, payment_id) FROM stdin;
\.


--
-- TOC entry 3353 (class 0 OID 16469)
-- Dependencies: 213
-- Data for Name: room_types; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.room_types (id, room_type, description, number_of_people, price) FROM stdin;
2	three_person	Pokój trzyosobowy z prywatną łazienką	3	150
3	family_room	Pokój rodzinny z prywatną łazienką	5	300
4	common	Pomieszczenia wspólne	0	0
1	two_person	Pokój dwuosobowy z prywatną łazienką	2	100
\.


--
-- TOC entry 3350 (class 0 OID 16426)
-- Dependencies: 210
-- Data for Name: rooms; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.rooms (id, name, description, room_type_id) FROM stdin;
3	pokoj_nr3	Pokoj nr 3	2
4	pokoj_nr4	Pokoj nr 4	1
5	pokoj_nr5	Pokoj nr 5	3
1	pokoj_nr1	Pokoj nr 1	2
2	pokoj_nr2	Pokoj nr 2	1
\.


--
-- TOC entry 3367 (class 0 OID 0)
-- Dependencies: 217
-- Name: booked_days_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.booked_days_id_seq', 127, true);


--
-- TOC entry 3368 (class 0 OID 0)
-- Dependencies: 220
-- Name: payments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.payments_id_seq', 7, true);


--
-- TOC entry 3369 (class 0 OID 0)
-- Dependencies: 215
-- Name: photos_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.photos_id_seq', 28, true);


--
-- TOC entry 3370 (class 0 OID 0)
-- Dependencies: 218
-- Name: reservations_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.reservations_id_seq', 6, true);


--
-- TOC entry 3371 (class 0 OID 0)
-- Dependencies: 212
-- Name: room_types_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.room_types_id_seq', 4, true);


--
-- TOC entry 3372 (class 0 OID 0)
-- Dependencies: 214
-- Name: rooms_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.rooms_id_seq', 17, true);


--
-- TOC entry 3199 (class 2606 OID 24679)
-- Name: booked_days booked_days_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booked_days
    ADD CONSTRAINT booked_days_pkey PRIMARY KEY (id);


--
-- TOC entry 3201 (class 2606 OID 24695)
-- Name: booked_days booked_days_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booked_days
    ADD CONSTRAINT booked_days_unique UNIQUE (booked_day, room_id);


--
-- TOC entry 3205 (class 2606 OID 24711)
-- Name: payments payments_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.payments
    ADD CONSTRAINT payments_pkey PRIMARY KEY (id);


--
-- TOC entry 3195 (class 2606 OID 16437)
-- Name: photos photos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.photos
    ADD CONSTRAINT photos_pkey PRIMARY KEY (id);


--
-- TOC entry 3203 (class 2606 OID 24703)
-- Name: reservations reservations_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT reservations_pkey PRIMARY KEY (id);


--
-- TOC entry 3197 (class 2606 OID 16473)
-- Name: room_types room_types_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.room_types
    ADD CONSTRAINT room_types_pkey PRIMARY KEY (id);


--
-- TOC entry 3193 (class 2606 OID 16430)
-- Name: rooms rooms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT rooms_pkey PRIMARY KEY (id);


--
-- TOC entry 3210 (class 2606 OID 24737)
-- Name: reservations payment; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT payment FOREIGN KEY (payment_id) REFERENCES public.payments(id) MATCH FULL NOT VALID;


--
-- TOC entry 3208 (class 2606 OID 24680)
-- Name: booked_days room; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.booked_days
    ADD CONSTRAINT room FOREIGN KEY (room_id) REFERENCES public.rooms(id);


--
-- TOC entry 3209 (class 2606 OID 24722)
-- Name: reservations room; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reservations
    ADD CONSTRAINT room FOREIGN KEY (room_id) REFERENCES public.rooms(id) NOT VALID;


--
-- TOC entry 3207 (class 2606 OID 16489)
-- Name: photos room_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.photos
    ADD CONSTRAINT room_id FOREIGN KEY (room_type_id) REFERENCES public.rooms(id) NOT VALID;


--
-- TOC entry 3206 (class 2606 OID 16474)
-- Name: rooms room_type; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.rooms
    ADD CONSTRAINT room_type FOREIGN KEY (room_type_id) REFERENCES public.room_types(id) ON UPDATE CASCADE ON DELETE RESTRICT;


-- Completed on 2023-01-06 16:24:15

--
-- PostgreSQL database dump complete
--

