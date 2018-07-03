ALTER TABLE "order_object" DROP CONSTRAINT IF EXISTS "order_object_fk0";

ALTER TABLE "brand" DROP CONSTRAINT IF EXISTS "brand_fk0";

ALTER TABLE "category" DROP CONSTRAINT IF EXISTS "category_fk0";

ALTER TABLE "category" DROP CONSTRAINT IF EXISTS "category_fk1";

ALTER TABLE "product" DROP CONSTRAINT IF EXISTS "product_fk0";

ALTER TABLE "product" DROP CONSTRAINT IF EXISTS "product_fk1";

ALTER TABLE "order_item" DROP CONSTRAINT IF EXISTS "order_item_fk0";

ALTER TABLE "order_item" DROP CONSTRAINT IF EXISTS "order_item_fk1";

ALTER TABLE "product_2_attribute" DROP CONSTRAINT IF EXISTS "product_2_attribute_fk0";

ALTER TABLE "product_2_attribute" DROP CONSTRAINT IF EXISTS "product_2_attribute_fk1";

ALTER TABLE "product_2_image" DROP CONSTRAINT IF EXISTS "product_2_image_fk0";

ALTER TABLE "product_2_image" DROP CONSTRAINT IF EXISTS "product_2_image_fk1";

DROP TABLE IF EXISTS "attribute";

DROP TABLE IF EXISTS "order_object";

DROP TABLE IF EXISTS "image";

DROP TABLE IF EXISTS "user_account";

DROP TABLE IF EXISTS "brand";

DROP TABLE IF EXISTS "category";

DROP TABLE IF EXISTS "product";

DROP TABLE IF EXISTS "order_item";

DROP TABLE IF EXISTS "product_2_attribute";

DROP TABLE IF EXISTS "product_2_image";
