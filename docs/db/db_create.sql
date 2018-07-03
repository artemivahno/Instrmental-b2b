CREATE TABLE "attribute" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"value" character varying(255) NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT attribute_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "order_object" (
	"id" serial NOT NULL,
	"close" BOOLEAN NOT NULL,
	"creator_id" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT order_object_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "image" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"url" character varying(255) NOT NULL,
	"position" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT image_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "user_account" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"email" character varying(255) NOT NULL,
	"password" character varying(255) NOT NULL,
	"enabled" BOOLEAN NOT NULL,
	"role" character varying(20) NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	CONSTRAINT user_account_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "brand" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"description" TEXT,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	"image_id" integer,
	CONSTRAINT brand_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "category" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"description" TEXT,
	"position" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	"image_id" integer,
	CONSTRAINT category_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "product" (
	"id" serial NOT NULL,
	"name" character varying(50) NOT NULL,
	"description" TEXT,
	"external_link" character varying(150) NOT NULL,
	"visible" BOOLEAN NOT NULL,
	"position" integer NOT NULL DEFAULT '0',
	"price" numeric(12,2) NOT NULL,
	"quantity_stock" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	"category_id" integer NOT NULL,
	"brand_id" integer NOT NULL,
	"version" integer NOT NULL,
	CONSTRAINT product_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "order_item" (
	"id" serial NOT NULL,
	"quantity" integer NOT NULL,
	"created" TIMESTAMP NOT NULL,
	"updated" TIMESTAMP NOT NULL,
	"order_object_id" integer NOT NULL,
	"product_id" integer NOT NULL,
	CONSTRAINT order_item_pk PRIMARY KEY ("id")
) WITH (
  OIDS=FALSE
);



CREATE TABLE "product_2_attribute" (
	"product_id" integer NOT NULL,
	"attribute_id" integer NOT NULL
) WITH (
  OIDS=FALSE
);



CREATE TABLE "product_2_image" (
	"product_id" integer NOT NULL,
	"image_id" integer NOT NULL
) WITH (
  OIDS=FALSE
);




ALTER TABLE "order_object" ADD CONSTRAINT "order_object_fk0" FOREIGN KEY ("creator_id") REFERENCES "user_account"("id");



ALTER TABLE "brand" ADD CONSTRAINT "brand_fk0" FOREIGN KEY ("image_id") REFERENCES "image"("id");

ALTER TABLE "category" ADD CONSTRAINT "category_fk0" FOREIGN KEY ("image_id") REFERENCES "image"("id");

ALTER TABLE "product" ADD CONSTRAINT "product_fk0" FOREIGN KEY ("category_id") REFERENCES "category"("id");
ALTER TABLE "product" ADD CONSTRAINT "product_fk1" FOREIGN KEY ("brand_id") REFERENCES "brand"("id");

ALTER TABLE "order_item" ADD CONSTRAINT "order_item_fk0" FOREIGN KEY ("order_object_id") REFERENCES "order_object"("id");
ALTER TABLE "order_item" ADD CONSTRAINT "order_item_fk1" FOREIGN KEY ("product_id") REFERENCES "product"("id");

ALTER TABLE "product_2_attribute" ADD CONSTRAINT "product_2_attribute_fk0" FOREIGN KEY ("product_id") REFERENCES "product"("id");
ALTER TABLE "product_2_attribute" ADD CONSTRAINT "product_2_attribute_fk1" FOREIGN KEY ("attribute_id") REFERENCES "attribute"("id");

ALTER TABLE "product_2_image" ADD CONSTRAINT "product_2_image_fk0" FOREIGN KEY ("product_id") REFERENCES "product"("id");
ALTER TABLE "product_2_image" ADD CONSTRAINT "product_2_image_fk1" FOREIGN KEY ("image_id") REFERENCES "image"("id");
