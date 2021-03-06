INSERT INTO marketplace.product ("name","category",model,manufacturer,description,price,rating)
VALUES
    ('Hasad Dunn',39,'Emily Giles','Nec Ante Maecenas Corp.','vitae diam. Proin dolor. Nulla semper tellus id nunc',95,49),
    ('Byron Wade',96,'Zane Newman','Nulla Integer LLP','neque. Nullam ut nisi a odio semper cursus.',413,54),
    ('Ocean Hicks',59,'Carl Cote','Aliquam Erat Corporation','odio. Nam interdum enim non nisi. Aenean eget',332,21),
    ('Devin Daniel',91,'Hedy Shepherd','Fames Associates','at sem molestie sodales. Mauris blandit enim consequat purus.',141,44),
    ('Gareth Shepherd',39,'Raja Jackson','Ipsum Dolor Sit Inc.','elit fermentum risus, at fringilla purus mauris',665,26),
    ('Anastasia Ball',19,'Dorian Ballard','Gravida Corporation','Quisque purus sapien, gravida non, sollicitudin a,',552,20),
    ('Iliana Madden',82,'Moses Snyder','Non Foundation','iaculis aliquet diam. Sed diam lorem,',33,36),
    ('Tate Hopkins',25,'Guy Hobbs','Aliquet Phasellus Company','Nulla dignissim. Maecenas ornare egestas ligula. Nullam feugiat',42,74),
    ('Erich Lane',21,'Ferris Lara','Primis In Institute','elit. Etiam laoreet, libero et tristique pellentesque, tellus sem',524,88),
    ('Dieter Mclean',58,'Berk Ferrell','Sagittis Industries','at, egestas a, scelerisque sed, sapien. Nunc pulvinar arcu et',538,37),
    ('Olympia Joyce',95,'Alice Joyce','Dictum Proin Company','feugiat placerat velit. Quisque varius. Nam',145,10),
    ('Nathaniel Neal',46,'Stella Foreman','Orci PC','nibh dolor, nonummy ac, feugiat non,',682,16),
    ('Brady Woods',65,'Lynn Burke','Phasellus Fermentum Corp.','mus. Proin vel nisl. Quisque fringilla euismod enim. Etiam gravida',169,92),
    ('Laura Wells',93,'Macon Small','Cursus Et Magna Foundation','amet, faucibus ut, nulla. Cras eu tellus eu',146,39),
    ('Jakeem Briggs',59,'Irene Santos','Aliquam Ltd','parturient montes, nascetur ridiculus mus. Aenean',412,76),
    ('Hannah Wood',13,'Igor Melton','Sit Amet Industries','est ac facilisis facilisis, magna tellus faucibus',476,71),
    ('Rooney Evans',67,'Beatrice Blanchard','Dictum Augue Malesuada LLC','Proin velit. Sed malesuada augue ut lacus.',516,1),
    ('Justina Romero',60,'Nell Allison','Convallis Institute','semper rutrum. Fusce dolor quam, elementum at, egestas a,',747,77),
    ('Candace Kerr',45,'Reed Leon','Ultrices LLC','Maecenas mi felis, adipiscing fringilla, porttitor vulputate,',939,67),
    ('Amelia Forbes',15,'Rose Cannon','Eros Turpis Non Incorporated','cursus a, enim. Suspendisse aliquet, sem',700,2);


INSERT INTO marketplace.store ("name",rating)
VALUES
    ('Curabitur Egestas Ltd',83),
    ('Molestie Incorporated',97),
    ('Mollis Foundation',48),
    ('Orci Ut Industries',68),
    ('Lobortis Risus Incorporated',2),
    ('Nec Tempus Mauris Associates',100),
    ('Ipsum Non Foundation',88),
    ('Tincidunt Ltd',78),
    ('Senectus LLP',36),
    ('Non Lorem Industries',48),
    ('Gravida Corp.',23),
    ('Laoreet Limited',88),
    ('Arcu Vivamus Associates',71),
    ('Pede Nunc Sed Institute',36),
    ('Aliquam Vulputate Institute',49),
    ('Mollis Vitae Ltd',89),
    ('Duis Sit Amet Limited',36),
    ('Eleifend Incorporated',72),
    ('Dolor Sit Corporation',90),
    ('Tortor Dictum Eu Limited',83);

INSERT INTO marketplace.product_offer (product,store,quantity)
VALUES
    (8,19,4),
    (15,7,30),
    (12,1,71),
    (4,14,76),
    (18,8,97),
    (19,15,90),
    (2,16,65),
    (6,11,60),
    (7,11,15),
    (19,8,99),
    (10,15,74),
    (9,3,55),
    (3,17,99),
    (12,8,76),
    (9,14,54),
    (1,3,91),
    (16,1,85),
    (12,4,24),
    (18,4,42),
    (16,13,56);
INSERT INTO marketplace.product_offer (product,store,quantity)
VALUES
    (18,12,96),
    (3,17,63),
    (18,12,73),
    (8,10,42),
    (17,3,20),
    (13,15,33),
    (19,10,75),
    (1,5,74),
    (18,15,39),
    (2,6,23),
    (8,4,48),
    (13,16,95),
    (17,16,49),
    (3,2,4),
    (5,11,54),
    (18,12,92),
    (7,8,72),
    (2,12,52),
    (11,1,58),
    (11,15,85);
INSERT INTO marketplace.product_offer (product,store,quantity)
VALUES
    (12,8,64),
    (5,13,80),
    (4,18,91),
    (6,9,80),
    (17,7,35),
    (18,14,52),
    (18,19,84),
    (17,11,83),
    (10,16,3),
    (16,10,14);
INSERT INTO marketplace.product_offer (product,store,quantity)
VALUES
    (8,10,45),
    (17,12,55),
    (4,5,49),
    (2,9,16),
    (12,11,96),
    (8,9,82),
    (17,19,74),
    (8,2,16),
    (12,15,80),
    (6,6,35),
    (19,9,95),
    (2,18,4),
    (17,9,28),
    (10,16,34),
    (8,1,8),
    (16,15,71),
    (4,7,74),
    (8,2,14),
    (7,17,57),
    (2,18,11);
INSERT INTO marketplace.product_offer (product,store,quantity)
VALUES
    (7,13,62),
    (4,14,43),
    (19,6,14),
    (3,6,66),
    (4,15,41),
    (11,14,2),
    (2,10,32),
    (8,18,39),
    (16,19,97),
    (4,9,0),
    (2,7,31),
    (11,10,51),
    (11,9,83),
    (11,2,3),
    (7,2,24),
    (12,14,22),
    (9,13,91),
    (7,7,24),
    (11,15,38),
    (9,15,79);
INSERT INTO marketplace.product_offer (product,store,quantity)
VALUES
    (3,18,39),
    (13,8,32),
    (10,2,77),
    (17,1,63),
    (11,2,50),
    (4,14,14),
    (4,7,21),
    (13,9,49),
    (6,2,72),
    (10,17,80);

INSERT INTO marketplace.customer(
    id, "name", address, stage, search_query, sorting_type_field, sorting_type_asc_desc, "offset", "limit", current_product_id)
VALUES (1, 'customer1', 'address1', 0, 'query', 0, 0, 0, 5, 1);

INSERT INTO marketplace."order"(
    customer, created_on)
VALUES (1, '2016-06-01 00:00');

INSERT INTO marketplace.order_item(
    "order", product_offer, quantity)
VALUES (1, 1, 9);

INSERT INTO marketplace.cart_item(
    customer, product_offer, quantity)
VALUES (1, 1, 9);

ALTER SEQUENCE marketplace.product_id_seq RESTART WITH 1000;
ALTER SEQUENCE marketplace.product_offer_id_seq RESTART WITH 1000;
ALTER SEQUENCE marketplace.store_id_seq RESTART WITH 1000;
ALTER SEQUENCE marketplace.cart_item_id_seq RESTART WITH 1000;
ALTER SEQUENCE marketplace.order_id_seq RESTART WITH 1000;
ALTER SEQUENCE marketplace.order_item_id_seq RESTART WITH 1000;