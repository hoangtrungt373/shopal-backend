BEGIN TRY
    BEGIN
        TRANSACTION


        ------------------------------------------------------------------------------------------------------------------------
        declare
            @sql_statement nvarchar(4000);
        declare
            @table_name varchar(50);

        drop table if exists #insert_table_name;
        create table #insert_table_name
        (
            name varchar(50)
        )
        insert into #insert_table_name
        values ('ACCOUNTING'),
               ('CATALOG'),
               ('COOPERATION_CONTRACT'),
               ('CUSTOMER'),
               ('ENTERPRISE'),
               ('MEMBERSHIP'),
               ('PRODUCT'),
               ('PRODUCT_CART'),
               ('PRODUCT_CATALOG'),
               ('PRODUCT_GALLERY'),
               ('PRODUCT_POINT'),
               ('PRODUCT_REVIEW'),
               ('PURCHASE_ORDER'),
               ('PURCHASE_ORDER_DETAIL');


------------------------------------------------------------------------------------------------------------------------
        DECLARE
            insert_table_name_cursor CURSOR FOR
                SELECT *
                FROM #insert_table_name
        OPEN insert_table_name_cursor;
        FETCH NEXT FROM insert_table_name_cursor INTO @table_name;
        PRINT
            @@FETCH_STATUS
        WHILE @@FETCH_STATUS = 0
            BEGIN
                SET
                    @sql_statement =
                                'IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE NAME = N''ID_ORIGIN'' AND OBJECT_ID = OBJECT_ID(N''shop.' +
                                @table_name + '''))
						  ALTER TABLE shop.' + @table_name + ' ADD ID_ORIGIN INT';
                EXEC sp_executesql @sql_statement;

                SET
                    @sql_statement = 'UPDATE shop.' + @table_name + ' SET ID_ORIGIN = ' + @table_name + '_ID';
                EXEC sp_executesql @sql_statement;

                SET
                    @sql_statement = 'ALTER TABLE shop.' + @table_name + ' ALTER COLUMN ID_ORIGIN INT NOT NULL';
                EXEC sp_executesql @sql_statement;

                FETCH NEXT FROM insert_table_name_cursor INTO @table_name;
            END;
        CLOSE insert_table_name_cursor;
        DEALLOCATE
            insert_table_name_cursor;

        drop table if exists #insert_table_name;


----------------------------------------------------------ROLLBACK TRANSACTION-----------------------------------------------------------
    COMMIT TRANSACTION
END TRY
BEGIN CATCH
    IF @@TRANCOUNT > 0
        ROLLBACK TRAN --RollBack in CASE of Error
    DECLARE
        @ErrorMessage NVARCHAR(4000);
    DECLARE
        @ErrorSeverity INT;
    DECLARE
        @ErrorState INT;

    SELECT @ErrorMessage = ERROR_MESSAGE() + ' occurred at Line_Number: ' + CAST(ERROR_LINE() AS VARCHAR(50)),
           @ErrorSeverity = ERROR_SEVERITY(),
           @ErrorState = ERROR_STATE();

    RAISERROR
        (@ErrorMessage, -- Message text.
        @ErrorSeverity, -- Severity.
        @ErrorState -- State.
        );
END CATCH