IMPORT FOREIGN SCHEMA public
    LIMIT TO (
    "Taxpayer"
    )
    FROM SERVER bu_taxpayer_server INTO taxpayer;
