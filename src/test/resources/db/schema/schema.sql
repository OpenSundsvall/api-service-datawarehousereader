
    create table kundinfo.vAgreements (
       agreementId int not null,
        billingId int not null,
        binding varchar(5) not null,
        bindingRule nvarchar(255),
        category nvarchar(255),
        customerid int not null,
        customerorgid varchar(8000),
        description varchar(4096),
        facilityId varchar(255),
        fromDate datetime,
        mainAgreement varchar(5) not null,
        toDate datetime,
        uuid uniqueidentifier,
        primary key (agreementId, billingId)
    );

    create table kundinfo.vCustomer (
       customerid int not null,
        organizationid varchar(10) not null,
        customerorgid varchar(255),
        customertype varchar(10) not null,
        organizationname nvarchar(255),
        primary key (customerid, organizationid)
    );

    create table kundinfo.vInstalledBase (
       InternalId int not null,
        CareOf varchar(255),
        City varchar(255),
        Company nvarchar(255),
        customerid int not null,
        DateFrom datetime,
        DateTo datetime,
        FacilityId varchar(255),
        HouseName varchar(255),
        Postcode varchar(255),
        Street varchar(255),
        Type nvarchar(255),
        primary key (InternalId)
    );

    create table kundinfo.vInstalledBaseMetadata (
       internalId int not null,
        company nvarchar(255),
        displayName varchar(26) not null,
        [key] varchar(255) not null,
        [type] varchar(8) not null,
        [value] nvarchar(255)
    );

    create table kundinfo.vInvoice (
       InvoiceNumber bigint not null,
        Administration nvarchar(255),
        AmountVatExcluded money,
        AmountVatIncluded money,
        CareOf varchar(255),
        City varchar(255),
        Currency varchar(3) not null,
        CustomerId int not null,
        CustomerType varchar(10),
        DueDate date,
        FacilityId varchar(255),
        InvoiceDate date,
        InvoiceDescription nvarchar(255),
        InvoiceName varchar(28) not null,
        InvoiceStatus nvarchar(255),
        InvoiceType nvarchar(255),
        OcrNumber bigint,
        OrganizationGroup varchar(11) not null,
        OrganizationId varchar(10),
        PdfAvailable varchar(5) not null,
        PostalCode varchar(255),
        ReversedVat varchar(5) not null,
        Rounding money,
        Street varchar(255),
        TotalAmount money,
        Vat money,
        VatEligiblaAmount money,
        primary key (InvoiceNumber)
    );

    create table kundinfo.vInvoiceDetail (
       invoiceProductSeq int not null,
        Amount money,
        AmountVatExcluded money,
        Description nvarchar(255),
        invoiceId int not null,
        InvoiceNumber bigint,
        OrganizationId varchar(10),
        periodFrom nvarchar(4000),
        periodTo nvarchar(4000),
        Productcode smallint not null,
        ProductName nvarchar(255),
        Quantity double precision,
        unit nvarchar(255),
        Unitprice money,
        Vat money,
        Vatrate double precision,
        primary key (invoiceProductSeq)
    );

    create table kundinfo.vMeasurementDistrictHeatingMonth (
       customerorgid varchar(8000) not null,
        facilityId varchar(255) not null,
        READING_SEQ int not null,
        feedType nvarchar(255),
        FeedTypeID tinyint not null,
        isInterpolted smallint not null,
        DateAndTime datetime,
        unit nvarchar(255),
        usage decimal(33,10),
        uuid uniqueidentifier,
        primary key (customerorgid, facilityId, READING_SEQ)
    );

    create table kundinfo.vMeasurementElectricityDay (
       customerorgid varchar(8000) not null,
        facilityId varchar(255) not null,
        feedType varchar(6) not null,
        isInterpolted tinyint not null,
        Date datetime not null,
        unit nvarchar(255) not null,
        Usage decimal(28,10) not null,
        uuid uniqueidentifier,
        primary key (customerorgid, facilityId, feedType, isInterpolted, Date, unit, Usage)
    );

    create table kundinfo.vMeasurementElectricityMonth (
       customerorgid varchar(8000) not null,
        facilityId varchar(255) not null,
        feedType varchar(6) not null,
        isInterpolted smallint not null,
        DateFrom datetime not null,
        unit nvarchar(255) not null,
        Usage decimal(28,10) not null,
        uuid uniqueidentifier,
        primary key (customerorgid, facilityId, feedType, isInterpolted, DateFrom, unit, Usage)
    );

    alter table kundinfo.vInstalledBaseMetadata 
       add constraint fk_installed_base_metadata_installed_base 
       foreign key (internalId) 
       references kundinfo.vInstalledBase;
