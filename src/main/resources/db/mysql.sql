create table add_contract_collection_status_number
(
    id     int auto_increment
        primary key,
    status int default 0 null,
    constraint add_contract_collection_status_number_id_uindex
        unique (id),
    constraint id
        unique (id)
);

create table add_sales_contract_registrant_id_status
(
    id     int auto_increment
        primary key,
    status int default 0 null,
    constraint add_sales_contract_registrant_id_status_id_uindex
        unique (id)
);

create table apply_for_car
(
    id                int auto_increment
        primary key,
    use_time          varchar(20) null,
    project_name      varchar(50) null,
    remark            varchar(200) null,
    direction         varchar(50) null,
    driver            varchar(20) null,
    check_state       varchar(10) null,
    applicant_id      varchar(50) null,
    run_state         varchar(10) null,
    mileage_start     int null,
    driver_id         varchar(50) null,
    start_time        varchar(20) null,
    end_time          varchar(20) null,
    create_time       datetime null,
    mileage_end       int null,
    remark2           varchar(200) null,
    project_id        varchar(50) null,
    mark_id           varchar(50) null,
    return_time       varchar(20) null,
    use_time_detailed varchar(20) null,
    constraint apply_for_car_id_uindex
        unique (id)
);

create table check_work
(
    id             varchar(40)   not null
        primary key,
    staff_id       varchar(40) null,
    staff_name     varchar(100) null,
    user_id        varchar(40) null,
    date           varchar(10) null,
    type           int default 1 not null,
    address_title  varchar(200)  not null,
    address_detail varchar(2000) not null,
    mac            varchar(100)  not null,
    notes          varchar(1000) not null,
    excption_type  varchar(1000) null,
    mediaids       text null
);

create table collection_notify
(
    id           int auto_increment
        primary key,
    collect_id   varchar(50) null,
    agreement_id varchar(50) null,
    notify_date  datetime null,
    notify_msg   varchar(2000) null
);

create table company_to_sdpf003
(
    id           varchar(40) null,
    jurisdiction varchar(50) null,
    street       varchar(50) null,
    phone        varchar(20) null,
    id_number    varchar(250) null
);

create table contract_collection_registration
(
    id                        int auto_increment
        primary key,
    collect_money             decimal(30, 2) null,
    collect_id                varchar(50) null,
    remark                    varchar(500) null,
    percent                   varchar(50) null,
    agreement_id              varchar(50) null,
    create_time               datetime null,
    registrant                varchar(50) null,
    pDate                     datetime null,
    status                    varchar(10) default '未审核' null,
    pMark                     varchar(50) null,
    create_time2              datetime null,
    agreement_name            varchar(100) null,
    status_number             int         default 0 null,
    collect_type              int         default 0 null,
    trade_acceptance_date     datetime null,
    trade_acceptance_interest decimal(30, 2) null,
    factoring_date            datetime null,
    factoring_time            varchar(20) null,
    fixed_assets              varchar(200) null,
    fixed_assets_status       int null,
    registrant_id             varchar(40) null,
    bill_mark                 varchar(20) null,
    mark                      varchar(40) null
);

create table contract_invoicing_registration
(
    invoice_money      decimal(30, 2) null,
    invoice_id         varchar(50) null,
    remark             varchar(500) null,
    percent            varchar(50) null,
    agreement_id       varchar(50) null,
    create_time        datetime null,
    registrant         varchar(50) null,
    pMark              varchar(50) null,
    bill_mark          varchar(20) null,
    agreement_name     varchar(100) null,
    return_date        datetime null,
    tax                varchar(20) null,
    status_number      int null,
    operator_name      varchar(40) null,
    operator_phone     varchar(20) null,
    operator_id_number varchar(20) null,
    province           varchar(50) null,
    city               varchar(50) null,
    district           varchar(50) null,
    street             varchar(50) null,
    party_a_needs      varchar(500) null,
    invoice_content    varchar(500) null,
    collected_money    decimal(30, 2) null,
    invoice_company    varchar(40) null,
    registrant_id      varchar(40) null,
    status             varchar(10) null,
    mark               int null,
    id                 varchar(40) not null
        primary key,
    uuid               varchar(40) null,
    constraint contract_invoicing_registration_id_uindex
        unique (id)
);

create table contract_invoicing_registrationfj
(
    id   varchar(40) null,
    name varchar(100) null,
    url  varchar(2000) null,
    mark varchar(40) null
);

create table ding_talk_department
(
    name       varchar(100) null,
    dept_id    bigint not null
        primary key,
    section_id varchar(40) null,
    constraint ding_talk_department_dept_id_uindex
        unique (dept_id)
);

create table ding_talk_link_notice_image
(
    id          varchar(40) not null
        primary key,
    name        varchar(100) null,
    status      int default 0 null,
    create_time datetime null,
    media_id    varchar(100) null,
    pic_url     varchar(100) null,
    constraint ding_talk_link_notice_image_id_uindex
        unique (id)
);

create table ding_talk_staff_info
(
    id                varchar(40) not null
        primary key,
    staff_id          varchar(40) null,
    ding_talk_user_id varchar(100) null,
    create_time       datetime null,
    union_id          varchar(100) null,
    constraint ding_talk_staff_info_id_uindex
        unique (id),
    constraint ding_talk_staff_info_staff_id_uindex
        unique (staff_id)
);

create table ding_talk_task_flow_record
(
    id              varchar(40) not null
        primary key,
    flow_approve_id varchar(40) null,
    staff_id        varchar(40) null,
    is_done         int null,
    task_id         varchar(150) null,
    creator_id      varchar(100) null,
    constraint ding_talk_task_flow_record_id_uindex
        unique (id)
);

create table entertain
(
    id                        varchar(40) null,
    section                   varchar(40) null,
    staff                     varchar(40) null,
    entertain_object          varchar(500) null,
    entertain_number          int null,
    accompanying_number       int null,
    entertain_reason          varchar(2000) null,
    cost_plan                 decimal(10, 2) null,
    cost_actual               decimal(10, 2) null,
    remark                    varchar(2000) null,
    entertain_types           varchar(50) null,
    entertain_type_other      varchar(500) null,
    entertain_tobacco_alcohol varchar(50) null,
    entertain_time            datetime null,
    entertain_category        varchar(50) null,
    entertain_way             int null,
    entertain_dining_standard int null,
    entertain_dining_other    varchar(500) null,
    entertain_car             int null,
    create_time               datetime null,
    update_time               datetime null,
    state                     int default 0 null
);

create table equipment_to_repair
(
    id              varchar(40) not null
        primary key,
    project_name    varchar(100) null,
    cause           varchar(2000) null,
    brand           varchar(100) null,
    equipment_name  varchar(100) null,
    serial_number   varchar(200) null,
    number          int null,
    remark          varchar(2000) null,
    staff_applicant varchar(40) null,
    staff_purchaser varchar(40) null,
    tracking_number varchar(100) null,
    state           int null,
    create_time     varchar(20) null,
    supplier        varchar(100) null,
    notify_flag     int default 0 null,
    notify_time     varchar(20) null,
    constraint equipment_to_repair_id_uindex
        unique (id)
);

create table equipment_to_repairfj
(
    equipment_to_repairFJ02 varchar(40) null,
    equipment_to_repairFJ03 varchar(40) null,
    equipment_to_repairFJ04 varchar(100) null,
    equipment_to_repairFJ05 varchar(2000) null,
    equipment_to_repairFJ01 varchar(40) not null
        primary key,
    constraint equipment_to_repairFJ_id_uindex
        unique (equipment_to_repairFJ01)
);

create table expense
(
    id          varchar(40) not null
        primary key,
    project     varchar(100) null,
    project_id  varchar(40) null,
    staff       varchar(40) null,
    total_money decimal(10, 2) null,
    apply_date  datetime null,
    create_date datetime null,
    remark      varchar(800) null,
    status      int default 0 null,
    constraint expense_id_uindex
        unique (id)
);

create table expense_subject
(
    id     varchar(40) not null
        primary key,
    course varchar(40) null,
    money  decimal(10, 2) null,
    staff  varchar(40) null,
    remark varchar(800) null,
    mark   varchar(40) null,
    constraint expense_subject_id_uindex
        unique (id)
);

create table expensefj
(
    expenseFJ01 varchar(40) not null
        primary key,
    expenseFJ02 varchar(300) null,
    expenseFJ03 varchar(500) null,
    expenseFJ04 varchar(40) null,
    constraint expenseFJ_expenseFJ01_uindex
        unique (expenseFJ01)
);

create table flow_count
(
    id           int auto_increment
        primary key,
    car_count    int null,
    person_count int null,
    count_date   varchar(20) null,
    staff_id     varchar(40) null,
    date         varchar(20) null
);

create table info
(
    id        varchar(36)   not null
        primary key,
    title     varchar(8000) not null,
    content   text null,
    send_id   varchar(36)   not null,
    send_time varchar(20)   not null
);

create table info_state
(
    id        varchar(36)   not null
        primary key,
    main_id   varchar(36)   not null,
    staff_id  varchar(36)   not null,
    state     int default 0 not null,
    read_date varchar(20) null
);

create table out_car_expense
(
    id         varchar(40) not null
        primary key,
    staff_id   varchar(40) not null,
    start_date varchar(20) null,
    end_date   varchar(40) null,
    system_km  decimal(10, 2) null,
    input_km   decimal(10, 2) null,
    remark     varchar(2000) null,
    files      varchar(8000) null,
    datetime   varchar(20) null,
    money      decimal(10, 2) null,
    state      int default 0 null
);

create table out_car_expense_detail
(
    id                 varchar(40) not null
        primary key,
    out_history_id     varchar(40) not null,
    out_car_expense_id varchar(40) null
);

create table out_car_history
(
    id               varchar(40)       not null
        primary key,
    staff_id         varchar(40)       not null,
    staff_name       varchar(100)      not null,
    section_id       varchar(40)       not null,
    section_name     varchar(40)       not null,
    start_latitude   varchar(20)       not null,
    start_longitude  varchar(40)       not null,
    start_time       varchar(20)       not null,
    start_addr_name  varchar(50)       not null,
    start_img        varchar(2000) null,
    end_latitude     varchar(20) null,
    end_longitude    varchar(20) null,
    end_time         varchar(20) null,
    end_addr_name    varchar(100) null,
    end_img          varchar(2000) null,
    datetime         varchar(20)       not null,
    system_km        decimal(10, 2) null,
    input_km         decimal(10, 2) null,
    is_parking_cost  tinyint default 0 not null,
    parking_cost_img varchar(2000) null,
    remark           varchar(2000) null,
    project_id       varchar(40) null,
    project_name     varchar(1000) null,
    toll             decimal(10, 2) null,
    smart_km         decimal(10, 2) null,
    start_smart_km   decimal(10, 2) null,
    end_smart_km     decimal(10, 2) null,
    car_type         int     default 0 null
);

create table out_tax
(
    out_id varchar(100)          not null
        primary key,
    tax    decimal(18) default 0 not null
);

create table performance
(
    id            varchar(40) not null
        primary key,
    log_id        varchar(40) null,
    flag          int null,
    weight        decimal(10, 2) null,
    score         decimal(10, 2) null,
    remark        varchar(2000) null,
    sign_staff_id varchar(40) null,
    sign_img      text null,
    staff_id      varchar(40) null,
    approve_id    varchar(40) null,
    datetime      varchar(20) null,
    month         varchar(10) null,
    note          varchar(2000) null,
    score_sum     decimal(10, 2) null,
    log_score     decimal(10, 2) null,
    section_id    varchar(40) null
);

create table person_head
(
    id  varchar(100) not null
        primary key,
    tag text         not null
);

create table person_head_msg
(
    id       varchar(100) not null
        primary key,
    staff_id varchar(100) not null,
    tag_id   varchar(100) not null
);

create table pro_apply_delete
(
    id     varchar(40)       not null
        primary key,
    pro_id varchar(40)       not null,
    remark varchar(2000) null,
    state  tinyint default 0 not null,
    date   varchar(20)       not null
);

create table pro_apply_dine
(
    id         varchar(40)   not null
        primary key,
    staff_id   varchar(40)   not null,
    note       varchar(8000) not null,
    date       varchar(10) null,
    datetime   varchar(20) null,
    state      int null,
    person_num int null,
    standard   varchar(1000) null,
    section_id varchar(40) null,
    project_id varchar(40) null,
    constraint pro_apply_dine_id_uindex
        unique (id)
);

create table pro_approve_notify
(
    approve_id varchar(100)  not null
        primary key,
    is_send    int default 0 not null
);

create table pro_bid
(
    id              varchar(40)       not null,
    project_name    varchar(200) null,
    company_name    varchar(200) null,
    company_id      varchar(40) null,
    project_id      varchar(40) null,
    bidding_dl_co   varchar(200) null,
    project_source  varchar(40) null,
    fee_note        varchar(500) null,
    bus_person      varchar(20) null,
    bus_person_id   varchar(40) null,
    self            tinyint null,
    cooperate       varchar(200) null,
    coordinate_name varchar(40) null,
    bid_money       decimal(10, 2) null,
    address         varchar(2000) null,
    date            varchar(10) null,
    type            varchar(10) null,
    remark          varchar(2000) null,
    staff_name      varchar(100) null,
    staff_id        varchar(40) null,
    datetime        varchar(20) null,
    bzj_date        varchar(10) null,
    bzj_money       decimal(10, 2) null,
    before_money    decimal(10, 2) null,
    in_date         varchar(10) null,
    state           tinyint null,
    bzj_state       tinyint default 0 not null,
    cost            varchar(200) null
);

create table pro_bzj
(
    id       int not null,
    money    decimal(11, 2) null,
    remark   varchar(2000) null,
    datetime varchar(20) null,
    pro_id   varchar(40) null,
    type     varchar(10) null
);

create table pro_course_invoke
(
    course_id   varchar(40) not null
        primary key,
    invoke_name varchar(200) null
);

create table pro_cpay_plan
(
    id          varchar(40)       not null
        primary key,
    pay_money   decimal(18)       not null,
    contract_id varchar(40)       not null,
    state       tinyint default 0 not null,
    pay_type_id varchar(20)       not null,
    flow_id     varchar(40) null,
    remark      varchar(2000) null
);

create table pro_detail
(
    company_id     varchar(100)              not null,
    company_name   varchar(1000)             not null,
    sale_person    varchar(100) null,
    tel            varchar(100) null,
    settle_type    varchar(100) null,
    tax            decimal(20, 2) default 0.00 null,
    id             varchar(100)              not null
        primary key,
    series         varchar(255)              not null,
    year_owe       decimal(20, 2) default 0.00 null,
    end_owe        decimal(20, 2) default 0.00 null,
    remark         varchar(1000) null,
    date           varchar(20)               not null,
    last_date      varchar(20)    default '' not null,
    last_staff     varchar(100)   default '' not null,
    staff          varchar(100)              not null,
    type           int                       not null,
    year_key       varchar(5)                not null,
    true_money     decimal(20, 2) default 0.00 null,
    owe_bill_money decimal(20, 2) default 0.00 null,
    company_belong int            default 1 null,
    year_pro       decimal(10, 2) null,
    yet_money      decimal(10, 2) null,
    put_money      decimal(10, 2) null,
    bill_money     decimal(10, 2) null,
    paper_owe      decimal(10, 2) null,
    year_bill      decimal(10, 2) null,
    sub_series     varchar(200) null
);

create table pro_detail_c
(
    pro_detail_id varchar(100)  not null,
    id            varchar(100)  not null
        primary key,
    project_id    varchar(100)  not null,
    project_name  varchar(1000) not null,
    pro_date      varchar(20) null,
    put_date      varchar(20) null,
    pro_money     decimal(18, 2) default 0.00 null,
    put_money     decimal(18, 2) default 0.00 null,
    staff         varchar(100)  not null,
    date          varchar(20)   not null,
    last_date     varchar(20)    default '' null,
    last_staff    varchar(100) null,
    pro_id        varchar(40) null,
    put_id        varchar(40) null,
    remark        varchar(8000) null
);

create table pro_detail_dp
(
    id            varchar(40) not null
        primary key,
    pro_detail_id varchar(40) not null,
    pay_money     decimal(18, 2) default 0.00 null,
    pay_date      varchar(20) null,
    dp_money      decimal(18, 2) default 0.00 null,
    dp_date       varchar(20) null,
    remark        varchar(8000) null
);

create table pro_detail_money
(
    id            varchar(100)              not null
        primary key,
    pro_detail_id varchar(100)              not null,
    series        varchar(1000)  default '' not null,
    remark        varchar(1000)  default '' not null,
    money         decimal(18, 2) default 0.00 null,
    date          varchar(20)               not null
);

create table pro_detail_owe
(
    pro_detail_id varchar(100)             not null,
    id            varchar(100)             not null
        primary key,
    owe_money     decimal(18, 2) default 0.00 null,
    owe_date      varchar(20)              not null,
    type          int            default 1 not null,
    date          varchar(20)              not null,
    staff         varchar(100)             not null
);

create table pro_detail_pay
(
    pro_detail_id varchar(100) not null,
    id            varchar(100) not null
        primary key,
    pay_money     decimal(18, 2) default 0.00 null,
    pay_date      varchar(20) null,
    bill_money    decimal(18, 2) default 0.00 null,
    bill_date     varchar(20) null,
    date          varchar(20)  not null,
    staff         varchar(100) not null,
    last_date     varchar(20) null,
    last_staff    varchar(100) null,
    detail_id     varchar(40) null
);

create table pro_dispose
(
    id              varchar(40)  not null
        primary key,
    title           varchar(100) not null,
    staff_id        varchar(40)  not null,
    exec_staff_id   varchar(40)  not null,
    exec_staff_name varchar(100) not null,
    reasons         varchar(8000) null,
    dispose_date    varchar(10)  not null,
    date            varchar(20) null,
    state           int null
);

create table pro_dispose_detail
(
    id         varchar(40) null,
    name       varchar(8000) null,
    sum        decimal(11, 2) null,
    money      decimal(11, 2) null,
    remark     varchar(8000) null,
    dispose_id varchar(40) null
);

create table pro_download_history
(
    id       varchar(40) not null
        primary key,
    pro_id   varchar(40) null,
    datetime varchar(20) null,
    staff_id varchar(40) null,
    state    tinyint null,
    img      text null
);

create table pro_enquiry
(
    id       varchar(40)       not null
        primary key,
    apply_id varchar(40)       not null,
    name     varchar(1000)     not null,
    date     datetime          not null,
    is_close tinyint default 0 not null,
    staff_id varchar(40)       not null
);

create table pro_enquiry_material
(
    id             varchar(40) not null
        primary key,
    enquiry_id     varchar(40) not null,
    mater_id       varchar(40) not null,
    date           datetime    not null,
    last_date      datetime null,
    apply_id       varchar(40) null,
    apply_mater_id varchar(40) null,
    num            decimal(18) null,
    price          decimal(18) null,
    remark         varchar(8000) null
);

create table pro_fa
(
    id       varchar(255) not null
        primary key,
    name     varchar(255) not null,
    series   varchar(255) null,
    model    varchar(255) null,
    pro_date varchar(10)  not null,
    section  varchar(255) null,
    person   varchar(255) null,
    have_sum decimal(18, 2) null,
    c_sum    decimal(18, 2) null,
    re_sum   decimal(18, 2) null,
    remark   varchar(255) null,
    type_id  varchar(40) null,
    date     varchar(20) null,
    fixed_id varchar(40) null,
    state    tinyint default 1 null,
    money    decimal(10, 2) null
);

create table pro_fa_folder
(
    id     varchar(40)  not null
        primary key,
    name   varchar(255) not null,
    root   varchar(255) null,
    parent varchar(255) null
);

create table pro_fixed_apply
(
    id         varchar(40)       not null
        primary key,
    title      varchar(100)      not null,
    datetime   datetime          not null,
    state      tinyint default 0 not null,
    staff_id   varchar(40)       not null,
    section_id varchar(40)       not null,
    reason     varchar(500)      not null
);

create table pro_fucai
(
    material_id varchar(100) not null
        primary key,
    price       decimal(10, 2) null,
    is_stop     int default 0 null
);

create table pro_house_bill
(
    id            varchar(40)    not null
        primary key,
    series        varchar(100) null,
    company_name  varchar(1000)  not null,
    mark          varchar(100) null,
    project_name  varchar(8000)  not null,
    money         decimal(11, 2) not null,
    in_money      decimal(11, 2) default 0.00 null,
    remark        varchar(8000)  default '' null,
    date          varchar(20)    not null,
    staff_id      varchar(40)    not null,
    contract_date varchar(10)    not null,
    end_date      varchar(10) null
);

create table pro_house_bill_detail
(
    id               varchar(40)                 not null
        primary key,
    house_bill_id    varchar(40)                 not null,
    type             int                         not null,
    money            decimal(11, 2) default 0.00 not null,
    remark           varchar(8000) null,
    series           varchar(100) null,
    end_date         varchar(10) null,
    is_approve       tinyint        default 0    not null,
    staff_id         varchar(40)                 not null,
    approve_staff_id varchar(40) null,
    date             varchar(10)                 not null,
    approve_date     varchar(10) null,
    in_date          varchar(10)                 not null
);

create table pro_house_json
(
    map_key varchar(40) null,
    json    text null
);

create table pro_material_history
(
    id          int auto_increment
        primary key,
    project_id  varchar(40)                 not null,
    material_id varchar(40)                 not null,
    plan_sum    decimal(10, 2) default 0.00 not null,
    plan_price  decimal(10, 2) default 0.00 not null,
    apply_sum   decimal(10, 2) default 0.00 not null,
    apply_price decimal(10, 2) default 0.00 not null,
    pro_sum     decimal(10, 2) default 0.00 not null,
    pro_price   decimal(10, 2) default 0.00 not null,
    put_sum     decimal(10, 2) default 0.00 not null,
    put_price   decimal(10, 2) default 0.00 not null,
    out_sum     decimal(10, 2) default 0.00 not null,
    out_price   decimal(10, 2) default 0.00 not null,
    back_sum    decimal(10, 2) default 0.00 not null,
    back_price  decimal(10, 2) default 0.00 not null,
    constraint pro_material_history_id_uindex
        unique (id)
);

create table pro_menu
(
    id         varchar(40)  not null
        primary key,
    name       varchar(255) not null,
    url        varchar(1000) null,
    parent     varchar(40) null,
    sort       int          not null,
    remark     varchar(1000) null,
    ico        varchar(255) null,
    type       int null,
    coding     varchar(200) null,
    is_Outer   tinyint null,
    outer_path varchar(8000) null,
    hide       tinyint default 0 null,
    bean_name  varchar(100) null,
    flow_sql   varchar(8000) null
);

create table pro_notify_type
(
    staff_id varchar(40)   not null
        primary key,
    type     int default 0 null,
    wx       int default 1 not null,
    ding     int default 0 not null,
    constraint pro_notify_type_staff_id_uindex
        unique (staff_id)
);

create table pro_other_pay
(
    id           varchar(40) not null
        primary key,
    company_id   varchar(40) null,
    company_name varchar(200) null,
    project_id   varchar(40) null,
    project_name varchar(200) null,
    pay_money    decimal(10, 2) null,
    pay_type_tag varchar(200) null,
    staff_id     varchar(40) null,
    state        tinyint default 0 null,
    datetime     datetime null,
    title        varchar(2000) null,
    remark       varchar(8000) null,
    bank_number  varchar(2000) null,
    open_account varchar(2000) null,
    pay_datetime datetime null,
    constraint pro_other_pay_id_uindex
        unique (id)
);

create table pro_other_pay_tag
(
    id   varchar(40) not null
        primary key,
    name varchar(100) null
);

create table pro_overtime
(
    id           varchar(100)   not null
        primary key,
    staff        varchar(100)   not null,
    date         varchar(10)    not null,
    overtime     varchar(20)    not null,
    begin_time   varchar(20) null,
    end_time     varchar(20) null,
    remark       varchar(1000) null,
    hour         decimal(10, 2) not null,
    name         varchar(255)   not null,
    state        tinyint null,
    approve_date varchar(20) null
);

create table pro_plate
(
    id       varchar(40)  not null
        primary key,
    staff_id varchar(40)  not null,
    plate    varchar(100) not null
);

create table pro_put_sign
(
    id            varchar(40) not null
        primary key,
    staff_id      varchar(40) not null,
    put_date      varchar(20) not null,
    sign_date     varchar(40) null,
    sign_staff_id varchar(40) null,
    type          int null,
    putObj        text        not null,
    sign_img      text null,
    put_id        varchar(40) null,
    past_date     varchar(10) null,
    pro_id        varchar(40) null
);

create table pro_quote
(
    id               varchar(40)                 not null
        primary key,
    enquiry_mater_id varchar(40) null,
    price            decimal(18, 2)              not null,
    remark           varchar(8000) null,
    datetime         datetime                    not null,
    tel              varchar(100)                not null,
    name             varchar(255)                not null,
    company          varchar(255) null,
    money            decimal(18, 2) default 0.00 not null,
    enquiry_id       varchar(40) null,
    mater_id         varchar(40) null
);

create table pro_report
(
    id                  bigint auto_increment
        primary key,
    pro_id              varchar(40) null,
    pro_material_id     varchar(40) null,
    material_id         varchar(40) null,
    voucher_date        varchar(20) null,
    company_id          varchar(40) null,
    sum                 decimal(10, 2) null,
    apply_price         decimal(10, 2) null,
    pro_price           decimal(10, 2) null,
    pro_money           decimal(10, 2) null,
    remark              varchar(8000) null,
    project_person_name varchar(20) null,
    project_id          varchar(40) null,
    project_name        varchar(2000) null,
    dh_remark           varchar(2000) null,
    accept_person_name  varchar(200) null,
    contract_remark     varchar(2000) null,
    pay_date            varchar(2000) null,
    company_name        varchar(200) null,
    staff_id            varchar(40) null,
    staff_name          varchar(100) null,
    series              varchar(1000) null
);

create table pro_role
(
    id      varchar(40) not null
        primary key,
    menu_id varchar(40) not null,
    role_id varchar(40) not null
);

create table pro_send
(
    id             varchar(40)             not null
        primary key,
    name           varchar(2000)           not null,
    date           varchar(20)             not null,
    address_detail varchar(8000)           not null,
    address_name   varchar(2000) default '' null,
    accept_person  varchar(200)            not null,
    tel            varchar(100)            not null,
    type           varchar(200)  default '' null,
    pay_money      varchar(1000) default '' null,
    staff_id       varchar(40)             not null,
    approve_state  tinyint       default 0 not null
);

create table pro_send_material
(
    pro_id      varchar(40)                 not null,
    id          varchar(40)                 not null
        primary key,
    material_id varchar(40)                 not null,
    y_num       decimal(11, 2) default 0.00 not null,
    s_num       decimal(11, 2) null,
    send_date   varchar(10) null,
    img_page    varchar(255) null,
    remark      varchar(2000) null
);

create table pro_staff_account
(
    id            varchar(40)                 not null
        primary key,
    staff_id      varchar(40)                 not null,
    balance       decimal(10, 2) default 0.00 not null,
    last_datetime varchar(20) null,
    staff_name    varchar(100)                not null,
    constraint pro_staff_account_id_uindex
        unique (id),
    constraint pro_staff_account_staff_id_uindex
        unique (staff_id)
);

create table pro_staff_balance_history
(
    id            int auto_increment
        primary key,
    staff_id      varchar(40)    not null,
    staff_name    varchar(100)   not null,
    money         decimal(10, 2) not null,
    type          tinyint        not null,
    datetime      varchar(20)    not null,
    after_balance decimal(10, 2) not null,
    remark        varchar(100)   not null,
    operate_id    varchar(40) null,
    operate_name  varchar(200) null,
    constraint pro_staff_balance_history_id_uindex
        unique (id)
);

create table pro_staff_hw
(
    id       int         not null
        primary key,
    staff_id varchar(40) not null,
    head     text null
);

create table pro_subcontract
(
    id           varchar(40)       not null
        primary key,
    name         varchar(1000)     not null,
    project_id   varchar(40) null,
    project_name varchar(1000)     not null,
    money        decimal(10, 2)    not null,
    party_b_id   varchar(40) null,
    party_b_name varchar(1000)     not null,
    staff_id     varchar(40)       not null,
    state        tinyint default 0 not null,
    remark       varchar(1000) null,
    datetime     varchar(20)       not null,
    sign_date    varchar(10)       not null,
    party_a_id   varchar(40) null,
    party_a_name varchar(1000) null,
    type         tinyint default 0 not null,
    start_date   varchar(10) null,
    end_date     varchar(10) null,
    constraint pro_subcontract_id_uindex
        unique (id)
);

create table pro_subcontract_pay
(
    id             varchar(40)       not null
        primary key,
    contract_id    varchar(40) null,
    pay_money      decimal(10, 2)    not null,
    contract_money decimal(10, 2)    not null,
    project_id     varchar(40)       not null,
    pay_info       varchar(2000)     not null,
    company_blank  varchar(100)      not null,
    company_number varchar(100)      not null,
    company_id     varchar(40)       not null,
    early_money    decimal(10, 2) null,
    account_money  decimal(10, 2) null,
    sum_pay_money  decimal(10, 2)    not null,
    remark         varchar(2000) null,
    datetime       varchar(20) null,
    state          tinyint default 0 not null,
    files_url      varchar(2000) null,
    staff          varchar(40) null,
    company_name   varchar(100) null,
    project_name   varchar(100) null,
    contract_name  varchar(100) null,
    constraint pro_subcontract_pay_id_uindex
        unique (id)
);

create table pro_subcontract_payfj
(
    pro_subcontract_payFJ01 varchar(40) null,
    pro_subcontract_payFJ02 varchar(40) not null
        primary key,
    pro_subcontract_payFJ03 varchar(2000) null,
    pro_subcontract_payFJ04 varchar(100) null
);

create table pro_subcontractfj
(
    pro_subcontractFJ01 varchar(40) null,
    pro_subcontractFJ02 varchar(40) not null
        primary key,
    pro_subcontractFJ03 varchar(100) null,
    pro_subcontractFJ04 varchar(2000) null,
    constraint pro_subcontractFJ_pro_subcontractFJ02_uindex
        unique (pro_subcontractFJ02)
);

create table pro_task_progress_report
(
    id                  varchar(40) not null
        primary key,
    project_name        varchar(100) null,
    project_id          varchar(40) null,
    construction_node   varchar(100) null,
    constructors_number int null,
    content             varchar(500) null,
    plan_for_tomorrow   varchar(500) null,
    pic_url             text null,
    progress_now        varchar(200) null,
    create_time         varchar(100) null,
    staff               varchar(40) null,
    constraint pro_task_progress_report_id_uindex
        unique (id)
);

create table pro_version
(
    id          int auto_increment
        primary key,
    name        varchar(200) not null,
    remark      varchar(2000) null,
    update_date varchar(20) null
);

create table pro_weixiu
(
    id              varchar(100) not null
        primary key,
    project_id      varchar(40) null,
    project_name    varchar(200) null,
    return_content  varchar(8000) null,
    datetime        varchar(20) null,
    is_service      int default 0 null,
    name            varchar(100) null,
    tel             varchar(100) null,
    files           text null,
    return_file     text null,
    return_staff_id varchar(40) null,
    return_time     varchar(20) null,
    staff_id        varchar(40) null,
    title           varchar(200) null
);

create table pro_weixiu_person
(
    id           varchar(40) not null
        primary key,
    staff_id     varchar(1000) null,
    staff_name   varchar(500) null,
    project_id   varchar(40) null,
    project_name varchar(40) null,
    date         varchar(20) not null,
    yj_date      varchar(10) null,
    expire_date  varchar(10) null,
    constraint pro_weixiu_person_id_uindex
        unique (id)
);

create table pro_work_check
(
    id             varchar(40) not null
        primary key,
    staff_id       varchar(40) null,
    date           varchar(10) null,
    time           varchar(10) null,
    type           tinyint null,
    address        varchar(8000) null,
    note           varchar(100) null,
    attache        text null,
    mac            varchar(100) null,
    column10       varchar(100) null,
    staff_name     varchar(100) null,
    lat            bigint null,
    lng            bigint null,
    sign_bg_avatar varchar(8000) null,
    overtime_hour  decimal(10, 2) null,
    overtime_id    varchar(40) null,
    leave_hour     decimal(10, 2) null,
    leave_id       varchar(40) null
);

create table pro_work_check_permission
(
    staff_id   varchar(40) not null,
    section_id varchar(40) not null,
    primary key (staff_id, section_id)
);

create table pro_work_check_show
(
    staff_id varchar(40) not null
        primary key,
    is_show  int         not null
);

create table pro_work_log
(
    id         bigint auto_increment
        primary key,
    staff_id   varchar(40) null,
    section_id varchar(40) null,
    datetime   datetime null,
    work_date  datetime null,
    content    varchar(8000) null,
    state      tinyint null,
    remark     varchar(8000) null,
    note       varchar(2000) null,
    weight     int null,
    score      decimal(10, 2) null,
    per_id     varchar(40) null,
    tag        varchar(20) null,
    edit       tinyint null,
    score_id   varchar(40) null
);

create table pro_work_log_file
(
    log_id  bigint      not null,
    file_id varchar(40) not null
);

create table pro_yq_data
(
    id             varchar(40)       not null
        primary key,
    departure_date varchar(10)       not null,
    arrive_addr    varchar(1000)     not null,
    tj_addr        varchar(8000) null,
    jt_tool        varchar(100)      not null,
    number         int               not null,
    back_date      varchar(10)       not null,
    user_name      varchar(255)      not null,
    staff_id       varchar(40)       not null,
    state          tinyint default 0 not null
);

create table pro_zujin
(
    id              int auto_increment
        primary key,
    series          varchar(200) null,
    company         varchar(200) null,
    brand           varchar(200) null,
    pay_type        int null,
    zl_type         int null,
    acreage         decimal(10, 2) null,
    zl_person       varchar(100) null,
    zl_person_tel   varchar(100) null,
    company_type_id int null,
    staff_name      varchar(100) null,
    is_dz           tinyint        default 0 null,
    is_sh           tinyint        default 0 null,
    ys_money        decimal(10, 2) default 0.00 null,
    sj_money        decimal(10, 2) default 0.00 null,
    kp_money        decimal(10, 2) default 0.00 null,
    cw_money        decimal(10, 2) default 0.00 null,
    remark          varchar(8000) null,
    staff_id        varchar(40) null,
    date_time       varchar(20) null,
    last_staff_id   varchar(40) null,
    last_date_time  varchar(20) null,
    dz_number       varchar(20) null,
    year_rental     decimal(10, 2) null,
    end_datetime    varchar(20) null,
    bzj_money       decimal(11, 2) null,
    bzj_type        varchar(10) null,
    return_money    decimal(11, 2) null,
    type            tinyint        default 0 null,
    start_datetime  varchar(20)    default '' null,
    files           varchar(2000) null
);

create table pro_zujin_house
(
    id         int auto_increment
        primary key,
    floor      varchar(100) null,
    pw_number  varchar(100) null,
    yetai_id   int null,
    type       varchar(100) null,
    acreage    varchar(100) null,
    flag       varchar(100) null,
    remark     varchar(8000) null,
    brand_name varchar(50) null,
    person     char(10) null
);

create table pro_zujin_house_r
(
    id       int auto_increment
        primary key,
    zj_id    int not null,
    house_id int not null,
    type     tinyint
);

create table pro_zujin_yt
(
    id   int auto_increment
        primary key,
    name varchar(100) null
);

create table project_author
(
    project_id varchar(40)              not null
        primary key,
    address    varchar(100)  default '' not null,
    command    varchar(8000) default '' not null,
    port       int                      not null,
    username   varchar(1000) default '' not null,
    password   varchar(1000) default '' null,
    pass_date  datetime null
);

create table project_task
(
    id            varchar(40)   not null
        primary key,
    name          varchar(2000) null,
    project_id    varchar(40)   not null,
    datetime      varchar(20)   not null,
    state         int default 0 not null,
    task_datetime varchar(20) null,
    staff_id      varchar(40) null,
    constraint project_task_id_uindex
        unique (id)
);

create table purchase_collect_registration
(
    id            int auto_increment
        primary key,
    collect_id    varchar(100) null,
    collect_money decimal(30, 2) null,
    percent       varchar(100) null,
    remark        varchar(500) null,
    collect_date  datetime null,
    registrant    varchar(50) null,
    mark          int null,
    pMark         varchar(100) null
);

create table purchase_contract_registration
(
    id              int auto_increment
        primary key,
    project_base    varchar(100) null,
    agreement_id    varchar(100) null,
    agreement_name  varchar(100) null,
    agreement_money decimal(30, 2) null,
    sign_date       datetime null,
    payment_days    int null,
    company_name    varchar(100) null,
    company_address varchar(100) null,
    bank_name       varchar(100) null,
    bank_account    varchar(100) null,
    remark          varchar(500) null,
    tak             varchar(100) null,
    collected_money decimal(30, 2) default 0.00 null,
    paid_money      decimal(30, 2) default 0.00 null,
    create_date     datetime null,
    registrant      varchar(50) null,
    constraint purchase_contract_registration_agreement_id_uindex
        unique (agreement_id)
);

create table purchase_contract_registration_files
(
    id        int auto_increment
        primary key,
    file_name varchar(100) null,
    file_url  varchar(100) null,
    mark      int null
);

create table purchase_pay_registration
(
    id         int auto_increment
        primary key,
    pay_id     varchar(100) null,
    pay_money  decimal(30, 2) null,
    percent    varchar(100) null,
    remark     varchar(500) null,
    pay_date   datetime null,
    registrant varchar(50) null,
    mark       int null,
    pMark      varchar(100) null,
    status     varchar(10) default '未审核' null,
    pDate      datetime null
);

create table sale_data
(
    id        varchar(40) not null
        primary key,
    date      varchar(20) null,
    sale_date varchar(20) null,
    brand     varchar(200) null,
    yetai     varchar(200) null,
    money     decimal(10, 2) null,
    tel       varchar(20) null,
    person    varchar(200) null,
    staff_id  varchar(40) null
);

create table sales_contract_logs
(
    id           varchar(40) not null
        primary key,
    agreement_id varchar(50) null,
    type         int null,
    modify_type  int null,
    modify_MSG   varchar(200) null,
    modify_staff varchar(40) null,
    create_time  datetime null,
    constraint sales_contract_logs_id_uindex
        unique (id)
);

create table sales_contract_manager
(
    id             varchar(40) not null
        primary key,
    agreement_id   varchar(40) null,
    manager_name   varchar(20) null,
    manager_id     varchar(40) null,
    create_time    datetime null,
    appoint_man_id varchar(40) null,
    section_name   varchar(20) null,
    constraint sales_contract_manager_id_uindex
        unique (id)
);

create table sales_contract_registrant_files
(
    id        int auto_increment
        primary key,
    file_name varchar(100) null,
    file_url  varchar(100) null,
    mark      varchar(40) null
);

create table sales_contract_registration
(
    agreement_id             varchar(100) null,
    agreement_name           varchar(100) null,
    agreement_money          decimal(30, 2) null,
    sign_date                datetime null,
    company_name             varchar(100) null,
    bank_name                varchar(100) null,
    bank_account             varchar(100) null,
    company_address          varchar(100) null,
    remark                   varchar(500) null,
    registrant               varchar(50) null,
    create_time              datetime null,
    invoiced_money           decimal(30, 2) default 0.00 null,
    collected_money          decimal(30, 2) default 0.00 null,
    notify_type              int            default 0 null,
    paymentDays              int null,
    projectBase              varchar(100) null,
    pDate                    datetime null,
    actualDate               datetime null,
    retention_money          decimal(30, 2) null,
    retention_percent        varchar(100)   default '0' null,
    id                       int null,
    tax                      varchar(100) null,
    invoice_company          varchar(40) null,
    city_id                  varchar(40) null,
    phone                    varchar(20) null,
    jurisdiction             varchar(50) null,
    street                   varchar(50) null,
    tax_id                   varchar(20) null,
    performance_bond         decimal(30, 2) null,
    performance_bond_percent varchar(10) null,
    final_accounts           decimal(30, 2) null,
    contact_man              varchar(20) null,
    registrant_id            varchar(40) null,
    modifier_id              varchar(40) null,
    contract_id              varchar(40) not null
        primary key,
    state                    int            default 0 null,
    lng                      decimal(10, 6),
    lat                      decimal(10, 6),
    constraint sales_contract_registration_agreement_id_uindex
        unique (agreement_id),
    constraint sales_contract_registration_contract_id_uindex
        unique (contract_id)
);

create table sales_contract_tax
(
    id             varchar(40) not null
        primary key,
    agreement_id   varchar(50) null,
    percent_for_AM varchar(10) null,
    tax_extra      varchar(10) null,
    adder_id       varchar(40) null,
    constraint sales_contract_tax_id_uindex
        unique (id)
);

create table sdeb002
(
    eb00201 varchar(10)             not null
        primary key,
    eb00202 varchar(30)             not null,
    eb00203 varchar(40)             not null,
    eb00204 varchar(30)             not null,
    eb00205 tinyint                 not null,
    eb00206 varchar(10)             not null,
    eb00207 varchar(10)             not null,
    eb00208 varchar(40)             not null,
    eb00209 varchar(255) default '' not null,
    eb00210 tinyint      default 0  not null
);

create table sdeb003
(
    eb00301 varchar(10)              not null
        primary key,
    eb00302 varchar(10)              not null,
    eb00303 varchar(10)              not null,
    eb00304 varchar(40)              not null,
    eb00305 int                      not null,
    eb00306 int                      not null,
    eb00307 varchar(30)              not null,
    eb00308 tinyint                  not null,
    eb00309 tinyint                  not null,
    eb00310 varchar(500)             not null,
    eb00311 tinyint                  not null,
    eb00312 int                      not null,
    eb00313 int                      not null,
    eb00314 varchar(3)               not null,
    eb00315 varchar(30)              not null,
    eb00316 int                      not null,
    eb00317 int                      not null,
    eb00318 tinyint       default 1  not null,
    eb00319 tinyint       default 0  not null,
    eb00320 varchar(2000) default '' not null
);

create table sdey003
(
    ey00301 varchar(50)  default '' not null
        primary key,
    ey00302 varchar(50)             not null,
    ey00303 varchar(50)             not null,
    ey00304 tinyint      default 0  not null,
    ey00305 varchar(500)            not null,
    ey00306 tinyint                 not null,
    ey00307 varchar(10)             not null,
    ey00308 tinyint                 not null,
    ey00309 varchar(200) default '' not null,
    ey00310 varchar(30)             not null,
    ey00311 varchar(30)             not null,
    ey00312 varchar(40)             not null,
    ey00313 varchar(50)             not null,
    ey00314 tinyint      default 0  not null,
    ey00315 varchar(100) default '' not null,
    ey00316 int          default 3  not null,
    ey00317 tinyint      default 0  not null,
    ey00318 varchar(200) default '' not null,
    ey00319 int          default 25 not null,
    ey00320 varchar(200) default '' not null,
    ey00321 varchar(200) default '' not null
);

create table sdfifoi
(
    FIFOI_01ID     decimal(18)    not null
        primary key,
    FIFOI_02Mat    varchar(40)    not null,
    FIFOI_03Date   varchar(24)    not null,
    FIFOI_04IQty   decimal(18, 6) not null,
    FIFOI_05RQty   decimal(18, 6) not null,
    FIFOI_06OQty   decimal(18, 6) not null,
    FIFOI_07Pri    decimal(18, 6) not null,
    FIFOI_08IAmt   decimal(18, 6) not null,
    FIFOI_09TranID varchar(40)    not null,
    FIFOI_10Type   tinyint        not null
);

create table sdfifoo
(
    FIFOO_01ID     decimal(18)    not null
        primary key,
    FIFOO_02Mat    varchar(40)    not null,
    FIFOO_03Date   varchar(24)    not null,
    FIFOO_04OQty   decimal(18, 6) not null,
    FIFOO_05RQty   decimal(18, 6) not null,
    FIFOO_06Pri    decimal(18, 6) not null,
    FIFOO_07OAmt   decimal(18, 6) not null,
    FIFOO_08TranID varchar(40)    not null,
    FIFOO_09Type   tinyint        not null,
    FIFOO_10FromID decimal(18)    not null
);

create table sdpa001
(
    pa00101 varchar(40)                     not null
        primary key,
    pa00102 varchar(200)                    not null,
    pa00103 varchar(500)                    not null,
    pa00104 tinyint                         not null,
    pa00105 varchar(40)                     not null,
    pa00106 varchar(80)                     not null,
    pa00107 tinyint                         not null,
    pa00108 varchar(10)                     not null,
    pa00109 varchar(10)                     not null,
    pa00110 varchar(40)                     not null,
    pa00111 varchar(10)                     not null,
    pa00112 varchar(20)                     not null,
    pa00113 varchar(20)                     not null,
    pa00114 varchar(10)                     not null,
    pa00115 varchar(10)                     not null,
    pa00116 varchar(10)                     not null,
    pa00117 varchar(10)                     not null,
    pa00118 varchar(10)                     not null,
    pa00119 varchar(40)                     not null,
    pa00120 varchar(40)                     not null,
    pa00121 decimal(18, 6)                  not null,
    pa00122 int                             not null,
    pa00123 int                             not null,
    pa00124 varchar(8)                      not null,
    pa00125 int                             not null,
    pa00126 varchar(40)                     not null,
    pa00127 varchar(40)                     not null,
    pa00128 varchar(300)                    not null,
    pa00129 tinyint                         not null,
    pa00130 tinyint                         not null,
    pa00131 varchar(20)                     not null,
    pa00132 varchar(10)                     not null,
    pa00133 varchar(10)                     not null,
    pa00134 varchar(2000)                   not null,
    pa00135 tinyint                         not null,
    pa00136 tinyint                         not null,
    pa00137 tinyint                         not null,
    pa00138 tinyint                         not null,
    pa00139 decimal(18, 6)                  not null,
    pa00140 varchar(100)   default ''       not null,
    pa00141 varchar(10)    default ''       not null,
    pa00142 varchar(10)    default ''       not null,
    pa00143 varchar(40)    default ''       not null,
    pa00144 int            default 0        not null,
    pa00145 varchar(10)    default ''       not null,
    pa00146 varchar(10)    default ''       not null,
    pa00147 int            default 0        not null,
    pa00148 varchar(40)    default ''       not null,
    pa00149 varchar(2000)  default ''       not null,
    pa00150 varchar(40)    default ''       not null,
    pa00151 tinyint        default 0        not null,
    pa00152 varchar(10)    default ''       not null,
    pa00153 varchar(40)    default ''       not null,
    pa00154 decimal(18, 6) default 0.000000 not null,
    pa00155 decimal(18, 6) default 0.000000 not null,
    pa00156 decimal(18, 6) default 0.000000 not null,
    pa00157 decimal(18, 6) default 0.000000 not null,
    pa00158 varchar(40)    default ''       not null,
    pa00159 smallint       default 1        not null,
    pa00160 decimal(18, 6) default 0.000000 not null,
    pa00161 decimal(18, 6) default 0.000000 not null,
    pa00162 varchar(40)    default ''       not null
);

create table sdpa007
(
    pa00701 varchar(8)             not null
        primary key,
    pa00702 varchar(100)           not null,
    pa00703 varchar(8)             not null,
    pa00704 varchar(80) default '' not null,
    pa00705 varchar(40) default '' not null
);

create table sdpa013
(
    pa01301 varchar(18)  not null
        primary key,
    pa01302 varchar(100) not null,
    pa01303 varchar(40)  not null
);

create table sdpa015
(
    pa01501 varchar(40)            not null
        primary key,
    pa01502 varchar(40)            not null,
    pa01503 varchar(40)            not null,
    pa01504 varchar(10)            not null,
    pa01505 varchar(40)            not null,
    pa01506 decimal(18, 6)         not null,
    pa01507 tinyint                not null,
    pa01508 varchar(8000)          not null,
    pa01509 varchar(10)            not null,
    pa01510 varchar(10)            not null,
    pa01511 varchar(20)            not null,
    pa01512 varchar(20)            not null,
    pa01513 varchar(500)           not null,
    pa01514 varchar(40) default '' not null,
    pa01515 varchar(40) default '' not null,
    pa01516 tinyint     default 0  not null,
    pa01517 varchar(40) default '' not null,
    pa01518 varchar(40) default '' not null,
    pa01519 varchar(40) default '' not null,
    pa01520 varchar(10) default '' not null,
    pa01521 tinyint     default 0  not null
);

create table sdpa015detail
(
    pa015Detail01 varchar(40)             not null
        primary key,
    pa015Detail02 varchar(40)             not null,
    pa015Detail03 smallint                not null,
    pa015Detail04 varchar(8)              not null,
    pa015Detail05 decimal(18, 6)          not null,
    pa015Detail06 varchar(255)            not null,
    pa015Detail07 varchar(40)  default '' not null,
    pa015Detail08 varchar(500) default '' not null,
    pa015Detail09 varchar(40)  default '' not null
);

create table sdpb006
(
    pb00601 varchar(8)             not null
        primary key,
    pb00602 varchar(100)           not null,
    pb00603 varchar(8)             not null,
    pb00604 varchar(80)            not null,
    pb00605 varchar(40)            not null,
    pb00606 int                    not null,
    pb00607 tinyint                not null,
    pb00608 varchar(255)           not null,
    pb00609 tinyint                not null,
    pb00610 varchar(40) default '' not null,
    pb00611 tinyint     default 0  not null,
    pb00612 tinyint     default 1  not null,
    pb00613 varchar(40) default '' not null
);

create table sdpb020
(
    pb02001 varchar(40)    default ''       not null
        primary key,
    pb02002 varchar(10)    default ''       not null,
    pb02003 varchar(40)    default ''       not null,
    pb02004 varchar(40)    default ''       not null,
    pb02005 varchar(40)    default ''       not null,
    pb02006 decimal(18, 6) default 0.000000 not null,
    pb02007 decimal(18, 6) default 0.000000 not null,
    pb02008 varchar(40)    default ''       not null,
    pb02009 decimal(18, 6) default 0.000000 not null,
    pb02010 tinyint        default 1        not null,
    pb02011 varchar(40)    default ''       not null,
    pb02012 decimal(18, 6) default 0.000000 not null,
    pb02013 decimal(18, 6) default 0.000000 not null,
    pb02014 decimal(18, 6) default 0.000000 not null,
    pb02015 decimal(18, 6) default 0.000000 not null,
    pb02016 varchar(500)   default ''       not null,
    pb02017 varchar(10)    default ''       not null,
    pb02018 varchar(10)    default ''       not null,
    pb02019 varchar(20)    default ''       not null,
    pb02020 varchar(20)    default ''       not null,
    pb02021 varchar(2000)  default ''       not null,
    pb02022 tinyint        default 0        not null,
    pb02023 varchar(40)    default ''       not null,
    pb02024 varchar(10)    default ''       not null,
    pb02025 varchar(40)    default ''       not null,
    pb02026 varchar(40)    default ''       not null,
    pb02027 varchar(40)    default ''       not null,
    pb02028 varchar(40)    default ''       not null,
    pb02029 varchar(40)    default ''       not null,
    pb02030 varchar(40)    default ''       not null,
    pb02031 varchar(10)    default ''       not null,
    pb02032 tinyint        default 0        not null,
    pb02033 varchar(40)    default '0'      not null,
    pb02034 decimal(18, 6) default 0.000000 not null,
    pb02035 tinyint        default 0        not null,
    pb02036 varchar(40)    default ''       not null
);

create table sdpb053
(
    pb05301 varchar(40)    not null
        primary key,
    pb05302 varchar(100)   not null,
    pb05303 varchar(20)    not null,
    pb05304 varchar(40)    not null,
    pb05305 varchar(40)    not null,
    pb05306 varchar(40)    not null,
    pb05307 decimal(18, 6) not null,
    pb05308 varchar(40)    not null,
    pb05309 varchar(40)    not null,
    pb05310 varchar(40)    not null,
    pb05311 varchar(40)    not null,
    pb05312 varchar(40)    not null,
    pb05313 varchar(40)    not null,
    pb05314 int            not null,
    pb05315 varchar(40)    not null,
    pb05316 varchar(40)    not null,
    pb05317 varchar(500)   not null,
    pb05318 varchar(40)    not null,
    pb05319 varchar(40)    not null,
    pb05320 varchar(100)   not null,
    pb05321 varchar(100)   not null,
    pb05322 varchar(40)    not null,
    pb05323 varchar(40)    not null
);

create table sdpd004
(
    pd00401 varchar(40)                     not null
        primary key,
    pd00402 varchar(200)                    not null,
    pd00403 varchar(18)                     not null,
    pd00404 varchar(40)                     not null,
    pd00405 varchar(40)                     not null,
    pd00406 varchar(40)                     not null,
    pd00407 varchar(10)                     not null,
    pd00408 decimal(18, 6) default 0.000000 not null,
    pd00409 decimal(18, 6)                  not null,
    pd00410 varchar(40)                     not null,
    pd00411 varchar(500)                    not null,
    pd00412 varchar(100)                    not null,
    pd00413 text                            not null,
    pd00414 varchar(18)                     not null,
    pd00415 varchar(18)                     not null,
    pd00416 tinyint                         not null,
    pd00417 varchar(20)                     not null,
    pd00418 decimal(18, 6)                  not null,
    pd00419 varchar(10)                     not null,
    pd00420 varchar(10)                     not null,
    pd00421 varchar(20)                     not null,
    pd00422 varchar(20)                     not null,
    pd00423 varchar(100)                    not null,
    pd00424 varchar(500)                    not null,
    pd00425 varchar(500)                    not null,
    pd00426 varchar(500)                    not null,
    pd00427 varchar(10)                     not null,
    pd00428 tinyint                         not null,
    pd00429 varchar(10)                     not null,
    pd00430 varchar(500)                    not null,
    pd00431 varchar(40)                     not null,
    pd00432 decimal(18, 6)                  not null,
    pd00433 decimal(18, 6)                  not null,
    pd00434 varchar(8)                      not null,
    pd00435 varchar(80)                     not null,
    pd00436 tinyint        default 1        not null,
    pd00437 decimal(18, 6) default 0.000000 not null,
    pd00438 decimal(18, 6) default 0.000000 not null,
    pd00439 varchar(40)    default ''       not null,
    pd00440 int            default 0        not null,
    pd00441 varchar(10)    default ''       not null,
    pd00442 varchar(20)    default ''       not null,
    pd00443 varchar(500)   default ''       not null,
    pd00444 decimal(18, 6) default 0.000000 not null,
    pd00445 decimal(18, 6) default 0.000000 not null,
    pd00446 decimal(18, 6) default 0.000000 not null,
    pd00447 decimal(18, 6) default 0.000000 not null,
    pd00448 decimal(18, 6) default 0.000000 not null,
    pd00449 decimal(18, 6) default 0.000000 not null,
    pd00450 varchar(40)    default ''       not null,
    pd00451 int            default 0        not null,
    pd00452 varchar(40)    default ''       not null,
    pd00453 varchar(1000)  default ''       not null,
    pd00454 varchar(10)    default ''       not null,
    pd00455 decimal(18, 6) default 0.000000 not null,
    pd00456 int            default 0        not null,
    pd00457 decimal(18, 6) default 0.000000 not null,
    pd00458 decimal(18, 6) default 0.000000 not null,
    pd00459 varchar(80)    default ''       not null,
    pd00460 varchar(80)    default ''       not null,
    pd00461 varchar(40)    default ''       not null,
    pd00462 varchar(10)    default ''       not null,
    pd00463 varchar(10)    default ''       not null,
    pd00464 varchar(10)    default ''       not null,
    pd00465 decimal(18, 6) default 0.000000 not null,
    pd00466 decimal(18, 6) default 0.000000 not null,
    pd00467 decimal(18, 6) default 0.000000 not null,
    pd00468 varchar(40)    default ''       not null,
    pd00469 varchar(40)    default ''       not null,
    pd00470 tinyint        default 3        not null,
    pd00471 varchar(40)    default ''       not null,
    pd00472 varchar(40)    default '0'      not null,
    pd00473 decimal(18, 6) default 0.000000 not null,
    pd00474 varchar(40)    default ''       not null,
    pd00475 decimal(18, 6) default 0.000000 not null,
    pd00476 decimal(18, 6) default 0.000000 not null,
    pd00477 varchar(10)    default ''       not null,
    pd00478 varchar(10)    default ''       not null,
    pd00479 varchar(40)    default ''       not null,
    pd00480 varchar(200)   default ''       not null,
    pd00481 tinyint        default 0        not null,
    pd00482 varchar(40)    default ''       not null,
    pd00483 varchar(10)    default ''       not null,
    pd00484 varchar(10)    default ''       not null,
    pd00485 decimal(18, 6) default 0.000000 not null,
    pd00486 varchar(40)    default ''       not null,
    pd00487 decimal(18, 6) default 1.000000 not null
);

create table sdpd006
(
    pd00601 varchar(18)              not null
        primary key,
    pd00602 varchar(100)             not null,
    pd00603 varchar(8)               not null,
    pd00604 varchar(80)              not null,
    pd00605 int           default 0  not null,
    pd00606 varchar(40)   default '' not null,
    pd00607 varchar(40)   default '' not null,
    pd00608 varchar(40)   default '' not null,
    pd00609 varchar(1000) default '' not null,
    pd00610 varchar(40)   default '' not null,
    pd00611 varchar(40)   default '' not null
);

create table sdpd013
(
    pd01301 varchar(40) not null,
    pd01302 varchar(40) not null,
    primary key (pd01301, pd01302)
);

create table sdpd017
(
    pd01701 varchar(40)                     not null,
    pd01702 varchar(40)                     not null
        primary key,
    pd01703 varchar(10)                     not null,
    pd01704 decimal(18, 6)                  not null,
    pd01705 varchar(1000)                   not null,
    pd01706 tinyint                         not null,
    pd01707 varchar(40)                     not null,
    pd01708 varchar(10)                     not null,
    pd01709 varchar(10)                     not null,
    pd01710 varchar(20)                     not null,
    pd01711 varchar(20)                     not null,
    pd01712 varchar(500)                    not null,
    pd01713 varchar(40)                     not null,
    pd01714 varchar(40)                     not null,
    pd01715 varchar(40)                     not null,
    pd01716 decimal(18, 6) default 0.000000 not null,
    pd01717 tinyint        default 0        not null,
    pd01718 varchar(20)    default ''       not null,
    pd01719 varchar(10)    default ''       not null,
    pd01720 varchar(500)   default ''       not null,
    pd01721 varchar(40)    default ''       not null,
    pd01722 varchar(40)    default '0'      not null,
    pd01723 decimal(18, 6) default 0.000000 not null,
    pd01724 decimal(18, 6) default 0.000000 not null,
    pd01725 decimal(18, 6) default 0.000000 not null,
    pd01726 varchar(40)    default ''       not null,
    pd01727 varchar(400)   default ''       not null,
    pd01728 decimal(18, 6) default 0.000000 not null,
    pd01729 decimal(18, 6) default 0.000000 not null,
    pd01730 decimal(18, 6) default 0.000000 not null,
    pd01731 decimal(18, 6) default 0.000000 not null,
    pd01732 varchar(40)    default ''       not null,
    pd01733 varchar(40)    default ''       not null,
    pd01734 int            default 0        not null,
    pd01735 varchar(40)    default ''       not null,
    pd01736 decimal(18, 6) default 0.000000 not null,
    pd01737 decimal(18, 6) default 0.000000 not null,
    pd01738 decimal(18, 6) default 0.000000 not null,
    pd01739 decimal(18, 6) default 0.000000 not null,
    pd01740 varchar(40)    default ''       not null
);

create table sdpd018
(
    pd01801 varchar(40)    not null,
    pd01802 varchar(40)    not null,
    pd01803 varchar(40)    not null,
    pd01804 decimal(18, 6) not null,
    pd01805 varchar(1000)  not null,
    pd01806 decimal(18, 6) not null,
    primary key (pd01801, pd01802, pd01803)
);

create table sdpd031
(
    pd03101 varchar(40)                     not null
        primary key,
    pd03102 varchar(40)                     not null,
    pd03103 varchar(100)                    not null,
    pd03104 varchar(40)                     not null,
    pd03105 varchar(40)                     not null,
    pd03106 varchar(40)                     not null,
    pd03107 varchar(500)                    not null,
    pd03108 varchar(10)                     not null,
    pd03109 decimal(18, 6)                  not null,
    pd03110 text                            not null,
    pd03111 varchar(1000)                   not null,
    pd03112 varchar(1000)                   not null,
    pd03113 text                            not null,
    pd03114 varchar(255)                    not null,
    pd03115 varchar(40)                     not null,
    pd03116 text                            not null,
    pd03117 varchar(40)    default ''       not null,
    pd03118 varchar(40)    default ''       not null,
    pd03119 tinyint        default 0        not null,
    pd03120 tinyint        default 0        not null,
    pd03121 varchar(40)    default ''       not null,
    pd03122 varchar(40)    default ''       not null,
    pd03123 varchar(40)    default ''       not null,
    pd03124 varchar(10)    default ''       not null,
    pd03125 tinyint        default 0        not null,
    pd03126 varchar(40)    default ''       not null,
    pd03127 varchar(10)    default ''       not null,
    pd03128 varchar(40)    default ''       not null,
    pd03129 decimal(18, 6) default 0.000000 not null,
    pd03130 decimal(18, 6) default 0.000000 not null,
    pd03131 varchar(40)    default ''       not null,
    pd03132 varchar(40)    default ''       not null,
    pd03133 decimal(18, 6) default 1.000000 not null
);

create table sdpd033
(
    pd03301 varchar(40) not null,
    pd03302 varchar(40) not null,
    primary key (pd03301, pd03302)
);

create table sdpd053
(
    pd05301 varchar(8)        not null
        primary key,
    pd05302 varchar(40)       not null,
    pd05303 int               not null,
    pd05304 varchar(255)      not null,
    pd05305 int     default 0 not null,
    pd05306 tinyint default 0 not null,
    pd05307 int     default 0 not null,
    pd05308 tinyint default 1 not null
);

create table sdpd064
(
    pd06401 varchar(40)    not null
        primary key,
    pd06402 varchar(100)   not null,
    pd06403 varchar(40)    not null,
    pd06404 varchar(40)    not null,
    pd06405 varchar(40)    not null,
    pd06406 varchar(40)    not null,
    pd06407 varchar(40)    not null,
    pd06408 tinyint        not null,
    pd06409 varchar(40)    not null,
    pd06410 varchar(40)    not null,
    pd06411 varchar(1000)  not null,
    pd06412 decimal(18, 6) not null,
    pd06413 varchar(40)    not null,
    pd06414 varchar(40)    not null,
    pd06415 decimal(18, 6) not null,
    pd06416 varchar(40)    not null,
    pd06417 varchar(200) null,
    pd06418 varchar(200) null
);

create table sdpd064fj
(
    pd064FJ01 varchar(40) not null,
    pd064FJ02 varchar(40) not null
);

create table sdpf001
(
    pf00101 varchar(8)              not null
        primary key,
    pf00102 varchar(100)            not null,
    pf00103 varchar(8)              not null,
    pf00104 varchar(80)             not null,
    pf00105 varchar(20) default '0' not null,
    pf00106 tinyint     default 0   not null
);

create table sdpf003
(
    pf00301 varchar(40)             not null
        primary key,
    pf00302 varchar(100)            not null,
    pf00303 varchar(18)             not null,
    pf00304 varchar(8000)           not null,
    pf00305 varchar(8000)           not null,
    pf00306 varchar(30)             not null,
    pf00307 varchar(18)             not null,
    pf00308 varchar(200)            not null,
    pf00309 varchar(80)  default '' not null,
    pf00310 varchar(20)             not null,
    pf00311 varchar(10)             not null,
    pf00312 varchar(10)             not null,
    pf00313 varchar(40)             not null,
    pf00314 varchar(20)             not null,
    pf00315 varchar(500)            not null,
    pf00316 tinyint                 not null,
    pf00317 varchar(20)  default '' not null,
    pf00318 varchar(50)  default '' not null,
    pf00319 varchar(50)  default '' not null,
    pf00320 varchar(50)  default '' not null,
    pf00321 varchar(50)  default '' not null,
    pf00322 varchar(500) default '' not null,
    pf00323 varchar(20)  default '' not null,
    pf00324 varchar(50)  default '' not null,
    pf00325 varchar(20)  default '' not null,
    pf00326 varchar(50)  default '' not null,
    pf00327 varchar(50)  default '' not null,
    pf00328 varchar(500) default '' not null,
    pf00329 varchar(20)  default '' not null,
    pf00330 varchar(50)  default '' not null,
    pf00331 varchar(50)  default '' not null,
    pf00332 varchar(50)  default '' not null,
    pf00333 varchar(50)  default '' not null,
    pf00334 varchar(500) default '' not null,
    pf00335 varchar(20)  default '' not null,
    pf00336 varchar(50)  default '' not null,
    pf00337 varchar(20)  default '' not null,
    pf00338 varchar(50)  default '' not null,
    pf00339 varchar(500) default '' not null,
    pf00340 varchar(80)  default '' not null,
    pf00341 varchar(200) default '' not null,
    pf00342 varchar(40)  default '' not null,
    pf00343 varchar(40)  default '' not null,
    pf00344 varchar(40)  default '' not null,
    pf00345 varchar(40)  default '' not null,
    pf00346 tinyint      default 1  not null,
    pf00347 int          default 7  not null,
    pf00348 tinyint      default 1  not null,
    pf00349 tinyint      default 1  not null
);

create table sdpj003
(
    pj00301 varchar(8)             not null
        primary key,
    pj00302 varchar(100)           not null,
    pj00303 varchar(8)             not null,
    pj00304 varchar(80)            not null,
    pj00305 varchar(40)            not null,
    pj00306 varchar(20)            not null,
    pj00307 varchar(50)            not null,
    pj00308 varchar(255)           not null,
    pj00309 varchar(40) default '' not null,
    pj00310 tinyint     default 3  not null,
    pj00311 varchar(40) default '' not null
);

create table sdpj004
(
    pj00401 varchar(40)    default ''       not null
        primary key,
    pj00402 varchar(100)   default ''       not null,
    pj00403 tinyint        default 0        not null,
    pj00404 varchar(100)   default ''       not null,
    pj00405 varchar(20)    default ''       not null,
    pj00406 varchar(10)    default ''       not null,
    pj00407 varchar(50)    default ''       not null,
    pj00408 varchar(300)   default ''       not null,
    pj00409 varchar(30)    default ''       not null,
    pj00410 varchar(20)    default ''       not null,
    pj00411 varchar(20)    default ''       not null,
    pj00412 tinyint        default 1        not null,
    pj00413 varchar(10)    default ''       not null,
    pj00414 varchar(80)    default ''       not null,
    pj00415 varchar(50)    default ''       not null,
    pj00416 varchar(50)    default ''       not null,
    pj00417 varchar(20)    default ''       not null,
    pj00418 decimal(18, 6) default 0.000000 not null,
    pj00419 tinyint        default 0        not null,
    pj00420 varchar(200)   default ''       not null,
    pj00421 varchar(50)    default ''       not null,
    pj00422 varchar(40)    default ''       not null,
    pj00423 varchar(40)    default ''       not null,
    pj00424 varchar(10)    default ''       not null,
    pj00425 varchar(10)    default ''       not null,
    pj00426 tinyint        default 0        not null,
    pj00427 varchar(40)    default ''       not null,
    pj00428 tinyint        default 0        not null,
    pj00429 tinyint        default 0        not null,
    pj00430 varchar(10)    default ''       not null,
    pj00431 tinyint        default 0        not null,
    pj00432 varchar(10)    default ''       not null,
    pj00433 varchar(40)    default ''       not null,
    pj00434 varchar(40)    default ''       not null,
    pj00435 varchar(100)   default ''       not null,
    pj00436 varchar(10)    default ''       not null,
    pj00437 varchar(40)    default ''       not null,
    pj00438 varchar(40)    default ''       not null,
    pj00439 varchar(40)    default ''       not null,
    pj00440 tinyint        default 0        not null,
    pj00441 varchar(100)   default ''       not null,
    pj00442 varchar(20)    default ''       not null,
    pj00443 varchar(40)    default ''       not null,
    pj00444 varchar(40)    default ''       not null,
    pj00445 varchar(100)   default ''       not null,
    pj00446 varchar(40)    default ''       not null,
    pj00447 tinyint        default 1        not null,
    pj00448 tinyint        default 0        not null,
    pj00449 varchar(100)   default ''       not null,
    pj00450 varchar(100)   default ''       not null,
    pj00451 varchar(2000) null,
    pj00452 varchar(1000) null,
    pj00453 varchar(10) null,
    pj00454 varchar(10) null,
    pj00455 varchar(1000) null,
    pj00456 varchar(1000) null,
    pj00457 varchar(10) null
);

create table sdpj004fj
(
    pj004FJ01 varchar(40) not null,
    pj004FJ02 varchar(40) not null,
    primary key (pj004FJ01, pj004FJ02)
);

create table sdpj005
(
    pj00501 varchar(8)   not null
        primary key,
    pj00502 varchar(100) not null,
    pj00503 varchar(255) not null,
    pj00504 varchar(40)  not null
);

create table sdpj006
(
    pj00601 varchar(8)  not null,
    pj00602 varchar(40) not null,
    primary key (pj00601, pj00602)
);

create table sdpj015
(
    pj01501 varchar(40)  not null
        primary key,
    pj01502 varchar(40)  not null,
    pj01503 varchar(100) not null,
    pj01504 varchar(255) not null
);

create table sdpj020
(
    pj02001 varchar(40)    not null
        primary key,
    pj02002 varchar(100)   not null,
    pj02003 decimal(18, 6) not null,
    pj02004 varchar(200)   not null
);

create table sdpk008
(
    pk00801 varchar(40)              not null
        primary key,
    pk00802 varchar(100)             not null,
    pk00803 varchar(100)             not null,
    pk00804 blob                     not null,
    pk00805 varchar(20)              not null,
    pk00806 varchar(20)              not null,
    pk00807 varchar(40)              not null,
    pk00808 varchar(40)              not null,
    pk00809 varchar(500)             not null,
    pk00810 int           default 0  not null,
    pk00811 varchar(40)   default '' not null,
    pk00812 varchar(40)   default '' not null,
    pk00813 varchar(2000) default '' not null
);

create table sdpm001
(
    pm00101 varchar(8)                      not null
        primary key,
    pm00102 varchar(100)                    not null,
    pm00103 varchar(8)                      not null,
    pm00104 varchar(80)                     not null,
    pm00105 varchar(40)    default ''       not null,
    pm00106 varchar(8)     default ''       not null,
    pm00107 decimal(18, 6) default 0.000000 not null,
    pm00108 varchar(60)    default ''       not null,
    pm00109 tinyint        default 0        not null
);

create table sdpm001s
(
    pm001S01 varchar(8)   not null
        primary key,
    pm001S02 varchar(10)  not null,
    pm001S03 varchar(40)  not null,
    pm001S04 varchar(100) not null,
    pm001S05 varchar(255) not null
);

create table sdpm002
(
    pm00201 varchar(40)                     not null
        primary key,
    pm00202 varchar(1000) null,
    pm00203 varchar(40)                     not null,
    pm00204 int                             not null,
    pm00205 varchar(8000) null,
    pm00206 varchar(8000)  default '' null,
    pm00207 decimal(18, 6)                  not null,
    pm00208 decimal(18, 6)                  not null,
    pm00209 decimal(18, 6)                  not null,
    pm00210 decimal(18, 6)                  not null,
    pm00211 decimal(18, 6)                  not null,
    pm00212 decimal(18, 6)                  not null,
    pm00213 decimal(18, 6)                  not null,
    pm00214 decimal(18, 6)                  not null,
    pm00215 tinyint        default 1        not null,
    pm00216 varchar(10)                     not null,
    pm00217 varchar(10)                     not null,
    pm00218 varchar(20)                     not null,
    pm00219 varchar(20)                     not null,
    pm00220 varchar(500)                    not null,
    pm00221 varchar(60)    default ''       not null,
    pm00222 varchar(60)    default ''       not null,
    pm00223 decimal(18, 6) default 0.000000 not null,
    pm00224 decimal(18, 6) default 0.000000 not null,
    pm00225 decimal(18, 6) default 0.000000 not null,
    pm00226 decimal(18, 6) default 0.000000 not null
);

create table sdpm003
(
    pm00301 varchar(40)    not null,
    pm00302 varchar(18)    not null,
    pm00303 tinyint        not null,
    pm00304 decimal(18, 6) not null,
    primary key (pm00301, pm00302)
);

create table sdpm004
(
    pm00401 varchar(40)            not null
        primary key,
    pm00402 varchar(100)           not null,
    pm00403 varchar(80)            not null,
    pm00404 varchar(20)            not null,
    pm00405 varchar(255)           not null,
    pm00406 varchar(10)            not null,
    pm00407 varchar(10)            not null,
    pm00408 varchar(20)            not null,
    pm00409 varchar(20)            not null,
    pm00410 varchar(2000)          not null,
    pm00411 varchar(40) default '' not null,
    pm00412 varchar(8)  default '' not null,
    pm00413 varchar(40) default '' not null,
    pm00414 varchar(40) default '' not null
);

create table sdpm004class
(
    pm004class01 varchar(8)              not null
        primary key,
    pm004class02 varchar(200) default '' not null,
    pm004class03 varchar(8)              not null,
    pm004class04 varchar(80)             not null,
    pm004class05 varchar(40)             not null,
    pm004class06 varchar(255)            not null
);

create table sdpm004right
(
    pm004Right01 varchar(40) not null,
    pm004Right02 varchar(40) not null,
    pm004Right03 smallint    not null,
    pm004Right04 smallint    not null,
    primary key (pm004Right01, pm004Right02)
);

create table sdpm005
(
    pm00501 varchar(40)                     not null,
    pm00502 varchar(40)                     not null,
    pm00503 decimal(18, 6)                  not null,
    pm00504 decimal(18, 6) default 0.000000 not null,
    pm00505 decimal(18, 6) default 0.000000 not null,
    pm00506 varchar(40)    default ''       not null,
    primary key (pm00501, pm00502, pm00506)
);

create table sdpm005_order
(
    storage_id   varchar(40) not null,
    material_id  varchar(40) null,
    put_datetime varchar(20) null
);

create table sdpm005bak
(
    pm005bak01 varchar(10)            not null,
    pm005bak02 varchar(40)            not null,
    pm005bak03 varchar(40)            not null,
    pm005bak04 decimal(18, 6)         not null,
    pm005bak05 decimal(18, 6)         not null,
    pm005bak06 decimal(18, 6)         not null,
    pm005bak07 varchar(40) default '' not null,
    primary key (pm005bak01, pm005bak02, pm005bak03, pm005bak07)
);

create table sdpm006
(
    pm00601 varchar(40)                     not null,
    pm00602 varchar(40)                     not null,
    pm00603 varchar(40)                     not null,
    pm00604 decimal(18, 6) default 0.000000 not null,
    pm00605 decimal(18, 6) default 0.000000 not null,
    pm00606 decimal(18, 6)                  not null,
    pm00607 decimal(18, 6)                  not null,
    pm00608 tinyint        default 1        not null,
    pm00609 varchar(20)    default ''       not null,
    pm00610 varchar(20)    default ''       not null,
    pm00611 decimal(18, 6) default 0.000000 not null,
    pm00612 decimal(18, 6) default 0.000000 not null,
    pm00613 decimal(18, 6) default 0.000000 not null,
    pm00614 varchar(18)    default ''       not null,
    pm00615 decimal(18, 6) default 1.000000 not null,
    pm00616 tinyint        default 0        not null,
    pm00617 varchar(20)    default ''       not null,
    pm00618 varchar(10)    default ''       not null,
    pm00619 decimal(18, 6) default 0.000000 not null,
    pm00620 decimal(18, 6) default 0.000000 not null,
    pm00621 decimal(18, 6) default 0.000000 not null,
    pm00622 varchar(1000)  default ''       not null,
    pm00623 varchar(40)    default ''       not null,
    pm00624 decimal(18, 6) default 0.000000 not null,
    primary key (pm00623)
);

create table sdpm013
(
    pm01301 varchar(40)                     not null
        primary key,
    pm01302 varchar(10)                     not null,
    pm01303 varchar(100)                    not null,
    pm01304 varchar(40)                     not null,
    pm01305 varchar(40)                     not null,
    pm01306 varchar(100)   default ''       not null,
    pm01307 varchar(255)                    not null,
    pm01308 varchar(20)                     not null,
    pm01309 varchar(10)                     not null,
    pm01310 varchar(20)                     not null,
    pm01311 varchar(20)                     not null,
    pm01312 varchar(2000)                   not null,
    pm01313 varchar(20)    default ''       not null,
    pm01314 int            default 0        not null,
    pm01315 varchar(40)    default ''       not null,
    pm01316 decimal(18, 6) default 0.000000 not null,
    pm01317 varchar(10)    default ''       not null,
    pm01318 tinyint        default 0        not null,
    pm01319 varchar(40)    default ''       not null,
    pm01320 varchar(40)    default ''       not null,
    pm01321 tinyint        default 0        not null,
    pm01322 varchar(20)    default '0'      not null,
    pm01323 varchar(500)   default ''       not null,
    pm01324 varchar(10)    default ''       not null,
    pm01325 varchar(40)    default ''       not null,
    pm01326 varchar(40)                     not null
);

create table sdpm013_dh
(
    dh01301 varchar(40) not null
        primary key,
    dh01302 varchar(10) null
);

create table sdpm013_discard
(
    pm01301 varchar(40) not null
        primary key,
    pm01302 varchar(10) null,
    pm01303 varchar(100) null,
    pm01304 varchar(40) null,
    pm01305 varchar(40) null,
    pm01306 varchar(100) default '' null,
    pm01307 varchar(255) null,
    pm01308 varchar(20) null,
    pm01309 varchar(10) null,
    pm01310 varchar(20) null,
    pm01311 varchar(20) null,
    pm01312 varchar(2000) null,
    pm01313 varchar(20)  default '' null,
    pm01314 int          default 0 null,
    pm01315 varchar(40) null,
    pm01316 decimal(18, 6) null,
    pm01317 varchar(10) null,
    pm01318 tinyint      default 0 null,
    pm01319 varchar(40) null,
    pm01320 varchar(40) null,
    pm01321 tinyint      default 0 null,
    pm01322 varchar(20)  default '0' null,
    pm01323 varchar(500) default '' null,
    pm01324 varchar(10)  default '' null,
    pm01325 varchar(40)  default '' null,
    pm01326 varchar(40)  default '' null
);

create table sdpm013fj
(
    pm013FJ01 varchar(40) not null,
    pm013FJ02 varchar(40) not null,
    primary key (pm013FJ01, pm013FJ02)
);

create table sdpm014
(
    pm01401 varchar(40)                     not null,
    pm01402 varchar(40)                     not null,
    pm01403 varchar(40)                     not null,
    pm01404 varchar(18)                     not null,
    pm01405 decimal(18, 6)                  not null,
    pm01406 decimal(18, 6)                  not null,
    pm01407 decimal(18, 6)                  not null,
    pm01408 decimal(18, 6)                  not null,
    pm01409 varchar(10)                     not null,
    pm01410 varchar(10)                     not null,
    pm01411 decimal(18, 6)                  not null,
    pm01412 varchar(10)    default ''       not null,
    pm01413 varchar(500)   default ''       not null,
    pm01414 decimal(18, 6) default 0.000000 not null,
    pm01415 decimal(18, 6) default 0.000000 not null,
    pm01416 decimal(18, 6) default 0.000000 not null,
    pm01417 varchar(1000)  default ''       not null,
    pm01418 varchar(40)    default ''       not null,
    pm01419 decimal(18, 6) default 0.000000 not null,
    pm01420 decimal(18, 6) default 0.000000 not null,
    pm01421 decimal(18, 6) default 0.000000 not null,
    pm01422 varchar(40)    default ''       not null,
    primary key (pm01401, pm01402)
);

create table sdpm014_discard
(
    pm01401 varchar(40) not null
        primary key,
    pm01402 varchar(40) null,
    pm01403 varchar(40) null,
    pm01404 varchar(18) null,
    pm01405 decimal(18, 6) null,
    pm01406 decimal(18, 6) null,
    pm01407 decimal(18, 6) null,
    pm01408 decimal(18, 6) null,
    pm01409 varchar(10) null,
    pm01410 varchar(10) null,
    pm01411 decimal(18, 6) null,
    pm01412 varchar(10)    default '' null,
    pm01413 varchar(500)   default '' null,
    pm01414 decimal(18, 6) default 0.000000 null,
    pm01415 decimal(18, 6) default 0.000000 null,
    pm01416 decimal(18, 6) default 0.000000 null,
    pm01417 varchar(1000)  default '' null,
    pm01418 varchar(40)    default '' null,
    pm01419 decimal(18, 6) default 0.000000 null,
    pm01420 decimal(18, 6) default 0.000000 null,
    pm01421 decimal(18, 6) default 0.000000 null,
    pm01422 varchar(40)    default '' null
);

create table sdpm015
(
    pm01501 varchar(40)            not null
        primary key,
    pm01502 varchar(10)            not null,
    pm01503 varchar(40)            not null,
    pm01504 varchar(40)            not null,
    pm01505 varchar(255)           not null,
    pm01506 varchar(10)            not null,
    pm01507 varchar(10)            not null,
    pm01508 varchar(20)            not null,
    pm01509 varchar(20)            not null,
    pm01510 varchar(500)           not null,
    pm01511 varchar(20) default '' not null,
    pm01512 varchar(10) default '' not null,
    pm01513 int         default 0  not null
);

create table sdpm016
(
    pm01601 varchar(40)    not null,
    pm01602 varchar(40)    not null
        primary key,
    pm01603 varchar(40)    not null,
    pm01604 decimal(18, 6) not null,
    pm01605 decimal(18, 6) not null,
    pm01606 decimal(18, 6) not null,
    pm01607 decimal(18, 6) not null
);

create table sdpm019
(
    pm01901 varchar(40)              not null
        primary key,
    pm01902 varchar(1000) null,
    pm01903 varchar(4000) default '' null,
    pm01904 varchar(255)  default '' not null,
    pm01905 varchar(400)  default '' null,
    pm01906 varchar(400)  default '' null,
    pm01907 varchar(400)  default '' null,
    pm01908 varchar(400)  default '' null,
    pm01909 varchar(400)  default '' null,
    pm01910 varchar(400)  default '' null,
    pm01911 datetime      default CURRENT_TIMESTAMP null
);

create table sdpm020
(
    pm02001 varchar(40)             not null
        primary key,
    pm02002 varchar(10)             not null,
    pm02003 varchar(40)             not null,
    pm02004 varchar(40)             not null,
    pm02005 varchar(40)             not null,
    pm02006 varchar(40)             not null,
    pm02007 varchar(40)             not null,
    pm02008 varchar(40)             not null,
    pm02009 varchar(255)            not null,
    pm02010 varchar(10)             not null,
    pm02011 varchar(10)             not null,
    pm02012 varchar(20)             not null,
    pm02013 varchar(20)             not null,
    pm02014 varchar(500)            not null,
    pm02015 varchar(40)  default '' not null,
    pm02016 varchar(10)  default '' not null,
    pm02017 varchar(20)  default '' not null,
    pm02018 varchar(100) default '' not null,
    pm02019 int          default 0  not null,
    pm02020 tinyint      default 0  not null,
    pm02021 varchar(40)  default '' not null
);

create table sdpm020fj
(
    pm020FJ01 varchar(40) not null,
    pm020FJ02 varchar(40) not null,
    primary key (pm020FJ01, pm020FJ02)
);

create table sdpm021
(
    pm02101 varchar(40)              not null,
    pm02102 varchar(40)              not null
        primary key,
    pm02103 varchar(40)              not null,
    pm02104 decimal(18, 6)           not null,
    pm02105 decimal(18, 6)           not null,
    pm02106 decimal(18, 6)           not null,
    pm02107 decimal(18, 6)           not null,
    pm02108 varchar(40)   default '' not null,
    pm02109 varchar(1000) default '' not null,
    pm02110 varchar(40)   default '' not null,
    pm02111 tinyint       default 0  not null,
    pm02112 varchar(40)   default '' not null
);

create table sdpm024
(
    pm02401 varchar(40)             not null
        primary key,
    pm02402 varchar(10)             not null,
    pm02403 varchar(40)             not null,
    pm02404 varchar(40)             not null,
    pm02405 varchar(40)             not null,
    pm02406 varchar(40)             not null,
    pm02407 varchar(40)             not null,
    pm02408 varchar(40)             not null,
    pm02409 varchar(255)            not null,
    pm02410 varchar(10)             not null,
    pm02411 varchar(10)             not null,
    pm02412 varchar(20)             not null,
    pm02413 varchar(20)             not null,
    pm02414 varchar(500)            not null,
    pm02415 varchar(40)  default '' not null,
    pm02416 varchar(10)  default '' not null,
    pm02417 varchar(20)  default '' not null,
    pm02418 varchar(100) default '' not null,
    pm02419 tinyint      default 0  not null,
    pm02420 tinyint      default 0  not null
);

create table sdpm025
(
    pm02501 varchar(40)              not null,
    pm02502 varchar(40)              not null
        primary key,
    pm02503 varchar(40)              not null,
    pm02504 decimal(18, 6)           not null,
    pm02505 decimal(18, 6)           not null,
    pm02506 decimal(18, 6)           not null,
    pm02507 varchar(40)   default '' not null,
    pm02508 varchar(1000) default '' not null,
    pm02509 varchar(40)   default '' not null,
    pm02510 varchar(1000) default '' not null
);

create table sdpm026
(
    pm02601 varchar(40)                     not null
        primary key,
    pm02602 varchar(10)                     not null,
    pm02603 varchar(40)                     not null,
    pm02604 varchar(40)                     not null,
    pm02605 varchar(40)                     not null,
    pm02606 varchar(255)                    not null,
    pm02607 varchar(10)                     not null,
    pm02608 varchar(10)                     not null,
    pm02609 varchar(20)                     not null,
    pm02610 varchar(20)                     not null,
    pm02611 varchar(500)                    not null,
    pm02612 varchar(40)    default ''       not null,
    pm02613 decimal(18, 6) default 0.000000 not null,
    pm02614 varchar(10)    default ''       not null,
    pm02615 varchar(100)   default ''       not null,
    pm02616 varchar(20)    default ''       not null,
    pm02617 int            default 0        not null,
    pm02618 varchar(40)    default ''       not null,
    pm02619 decimal(18, 6) default 0.000000 not null,
    pm02620 tinyint        default 0        not null,
    pm02621 varchar(40)    default ''       not null,
    pm02622 varchar(40)    default ''       not null,
    pm02623 tinyint        default 0        not null,
    pm02624 tinyint        default 0        not null,
    pm02625 varchar(40)    default ''       not null,
    pm02626 varchar(40)    default ''       not null,
    pm02627 varchar(500)   default ''       not null,
    pm02628 varchar(10)    default ''       not null,
    pm02629 varchar(40)    default ''       not null
);

create table sdpm027
(
    pm02701 varchar(40)                         not null,
    pm02702 varchar(40)                         not null,
    pm02703 varchar(40)                         not null,
    pm02704 varchar(18)                         not null,
    pm02705 decimal(18, 6)                      not null,
    pm02706 decimal(18, 6)                      not null,
    pm02707 decimal(18, 6)                      not null,
    pm02708 decimal(18, 6)                      not null,
    pm02709 decimal(18, 6) default 0.000000     not null,
    pm02710 decimal(18, 6) default 0.000000     not null,
    pm02711 decimal(18, 6) default 0.000000     not null,
    pm02712 varchar(40)    default ''           not null,
    pm02713 varchar(1000)  default ''           not null,
    pm02714 decimal(18, 6) default 0.000000     not null,
    pm02715 decimal(18, 6) default 0.000000     not null,
    pm02716 decimal(18, 6) default 0.000000     not null,
    pm02717 decimal(18, 6) default 0.000000     not null,
    pm02718 varchar(40)    default ''           not null,
    pm02719 int            default 0            not null,
    pm02720 varchar(40)    default '2010-06-01' not null,
    pm02721 varchar(40)    default ''           not null,
    pm02722 decimal(18, 6) default 0.000000     not null,
    pm02723 varchar(40)    default ''           not null,
    primary key (pm02701, pm02702)
);

create table sdpm034
(
    pm03401 varchar(40)              not null
        primary key,
    pm03402 varchar(40)              not null,
    pm03403 varchar(40)              not null,
    pm03404 tinyint                  not null,
    pm03405 varchar(20)   default '' not null,
    pm03406 varchar(10)              not null,
    pm03407 varchar(20)   default '' not null,
    pm03408 int                      not null,
    pm03409 varchar(255)  default '' not null,
    pm03410 varchar(40)   default '' not null,
    pm03411 varchar(40)   default '' not null,
    pm03412 int           default 0  not null,
    pm03413 varchar(40)   default '' not null,
    pm03414 varchar(10)   default '' not null,
    pm03415 varchar(10)   default '' not null,
    pm03416 varchar(2000) default '' not null,
    pm03417 varchar(40)   default '' not null,
    pm03418 tinyint       default 0  not null,
    pm03419 varchar(40)   default '' not null,
    pm03420 int           default 0  not null,
    pm03421 varchar(500)  default '' not null,
    pm03422 varchar(10)   default '' not null,
    pm03423 varchar(40)   default '' not null
);

create table sdpm035
(
    pm03501 varchar(40)                     not null,
    pm03502 int                             not null,
    pm03503 varchar(40)                     not null,
    pm03504 varchar(10)                     not null,
    pm03505 decimal(18, 6)                  not null,
    pm03506 decimal(18, 6)                  not null,
    pm03507 varchar(40)                     not null,
    pm03508 decimal(18, 6)                  not null,
    pm03509 varchar(255)                    not null,
    pm03510 int            default 0        not null,
    pm03511 varchar(40)    default ''       not null,
    pm03512 varchar(40)    default ''       not null,
    pm03513 varchar(1000)  default ''       not null,
    pm03514 varchar(18)    default ''       not null,
    pm03515 decimal(18, 6) default 1.000000 not null,
    pm03516 decimal(18, 6) default 0.000000 not null,
    pm03517 decimal(18, 6) default 0.000000 not null,
    pm03518 decimal(18, 6) default 0.000000 not null,
    pm03519 varchar(40)    default ''       not null,
    pm03520 varchar(40)    default ''       not null,
    pm03521 tinyint        default 0        not null,
    pm03522 varchar(40)    default ''       not null,
    primary key (pm03501, pm03512)
);

create table sdpm058
(
    pm05801 varchar(100) not null,
    pm05802 varchar(100) not null,
    primary key (pm05801, pm05802)
);

create table sdpm070
(
    pm07001 varchar(40)                     not null,
    pm07002 varchar(40)                     not null,
    pm07003 varchar(40)                     not null,
    pm07004 varchar(40)                     not null,
    pm07005 decimal(18, 6) default 0.000000 not null,
    pm07006 decimal(18, 6) default 0.000000 not null,
    pm07007 decimal(18, 6)                  not null,
    pm07008 decimal(18, 6)                  not null,
    pm07009 tinyint        default 1        not null,
    pm07010 varchar(20)    default ''       not null,
    pm07011 varchar(20)    default ''       not null,
    pm07012 decimal(18, 6) default 0.000000 not null,
    pm07013 decimal(18, 6) default 0.000000 not null,
    pm07014 decimal(18, 6) default 0.000000 not null,
    pm07015 varchar(18)    default ''       not null,
    pm07016 decimal(18, 6) default 1.000000 not null,
    pm07017 tinyint        default 0        not null,
    pm07018 varchar(20)    default ''       not null,
    pm07019 varchar(10)    default ''       not null,
    pm07020 decimal(18, 6) default 0.000000 not null,
    pm07021 decimal(18, 6) default 0.000000 not null,
    pm07022 decimal(18, 6) default 0.000000 not null,
    pm07023 varchar(1000)  default ''       not null,
    pm07024 varchar(40)    default ''       not null,
    pm07025 int            default 0        not null,
    primary key (pm07001, pm07002, pm07003, pm07004, pm07024)
);

create table sdpm070f
(
    pm070F01 varchar(40) not null,
    pm070F02 varchar(40) not null,
    primary key (pm070F01, pm070F02)
);

create table sdpm071
(
    pm07101 varchar(40)  not null
        primary key,
    pm07102 varchar(40)  not null,
    pm07103 varchar(40)  not null,
    pm07104 varchar(40)  not null,
    pm07105 varchar(200) not null,
    pm07106 varchar(10)  not null,
    pm07107 varchar(20)  not null,
    pm07108 varchar(10)  not null,
    pm07109 tinyint      not null,
    pm07110 varchar(20)  not null,
    pm07111 varchar(10)  not null,
    pm07112 varchar(400) not null
);

create table sdpo001
(
    po00101 varchar(40)             not null
        primary key,
    po00102 varchar(100)            not null,
    po00103 varchar(255) null,
    po00104 varchar(8)  default ''  not null,
    po00105 int         default 0   not null,
    po00106 tinyint     default 0   not null,
    po00107 varchar(10) default ''  not null,
    po00108 varchar(40) default '0' not null,
    po00109 varchar(40) default ''  not null,
    po00110 varchar(40) default ''  not null,
    po00111 tinyint     default 0   not null
);

create table sdpo001_history
(
    po00101 varchar(40)            not null
        primary key,
    po00102 varchar(100)           not null,
    po00103 varchar(255) null,
    po00104 varchar(8)  default '' not null,
    po00105 int         default 0  not null,
    po00106 tinyint     default 0  not null,
    po00107 varchar(10) default '' not null
);

create table sdpo002
(
    po00201 varchar(40)            not null
        primary key,
    po00202 varchar(40)            not null,
    po00203 int                    not null,
    po00204 int                    not null,
    po00205 varchar(400)           not null,
    po00206 varchar(200)           not null,
    po00207 int                    not null,
    po00208 varchar(40) default '' not null,
    po00209 int         default 0  not null,
    po00210 tinyint     default 0  not null,
    po00211 tinyint     default 0  not null,
    po00212 tinyint                not null
);

create table sdpo002b
(
    po002b01 varchar(40)             not null,
    po002b02 varchar(40)             not null,
    po002b03 int                     not null,
    po002b04 int                     not null,
    po002b05 varchar(400)            not null,
    po002b06 varchar(200)            not null,
    po002b07 int                     not null,
    po002b08 varchar(40)  default '' not null,
    po002b09 int          default 0  not null,
    po002b10 tinyint      default 0  not null,
    po002b11 varchar(400) default '' not null,
    po002b12 tinyint      default 0  not null,
    po002b13 tinyint                 not null,
    primary key (po002b01, po002b02)
);

create table sdpo002b_history
(
    po002b01         varchar(40)             not null,
    po002b02         varchar(40)             not null,
    po002b03         int                     not null,
    po002b04         int                     not null,
    po002b05         varchar(400)            not null,
    po002b06         varchar(200)            not null,
    po002b07         int                     not null,
    po002b08         varchar(40)  default '' not null,
    po002b09         int          default 0  not null,
    po002b10         tinyint      default 0  not null,
    po002b11         varchar(400) default '' not null,
    po002b12         tinyint      default 0  not null,
    po002b_History13 tinyint                 not null,
    primary key (po002b01, po002b02)
);

create table sdpo003
(
    po00301 varchar(40)              not null
        primary key,
    po00302 varchar(19)              not null,
    po00303 varchar(40)              not null,
    po00304 varchar(100)             not null,
    po00305 varchar(3000)            not null,
    po00306 varchar(10)              not null,
    po00307 varchar(400)             not null,
    po00308 int                      not null,
    po00309 int                      not null,
    po00310 int                      not null,
    po00311 varchar(40)              not null,
    po00312 int                      not null,
    po00313 varchar(19)   default '' not null,
    po00314 varchar(40)   default '' not null,
    po00315 varchar(4000) default '' not null,
    po00316 varchar(40)   default '' not null,
    po00317 varchar(40)   default '' not null
);

create table sdpo003_history
(
    po00301         varchar(40)              not null
        primary key,
    po00302         varchar(19)              not null,
    po00303         varchar(40)              not null,
    po00304         varchar(100)             not null,
    po00305         varchar(3000)            not null,
    po00306         varchar(10)              not null,
    po00307         varchar(400)             not null,
    po00308         int                      not null,
    po00309         int                      not null,
    po00310         int                      not null,
    po00311         varchar(40)              not null,
    po00312         int                      not null,
    po00313         varchar(19)   default '' not null,
    po00314         varchar(40)   default '' not null,
    po00315         varchar(4000) default '' not null,
    po00316         varchar(40)   default '' not null,
    po003_History17 varchar(40)   default '' not null
);

create table sdpo004
(
    po00401 varchar(40)              not null
        primary key,
    po00402 varchar(40)              not null,
    po00403 varchar(40)              not null,
    po00404 varchar(40)              not null,
    po00405 varchar(40)              not null,
    po00406 varchar(19)              not null,
    po00407 varchar(19)              not null,
    po00408 varchar(19)              not null,
    po00409 int                      not null,
    po00410 varchar(2000) default '' not null,
    po00411 int                      not null,
    po00412 int                      not null,
    po00413 varchar(19)   default '' null,
    po00414 tinyint       default 0  not null,
    po00415 varchar(3)    default '' not null,
    po00416 tinyint       default 0  not null,
    po00417 varchar(19)   default '' not null,
    po00418 varchar(40)   default '' not null,
    po00419 tinyint       default 0  not null,
    po00420 tinyint       default 0  not null,
    po00421 varchar(40)              not null
);

create table sdpo004_allrecord
(
    po00401 varchar(40)              not null,
    po00402 varchar(40)              not null,
    po00403 varchar(40)              not null,
    po00404 varchar(40)              not null,
    po00405 varchar(40)              not null,
    po00406 varchar(19)              not null,
    po00407 varchar(19)              not null,
    po00408 varchar(19)              not null,
    po00409 int                      not null,
    po00410 varchar(2000) default '' not null,
    po00411 int                      not null,
    po00412 int                      not null,
    po00413 varchar(19)   default '' null,
    po00414 tinyint       default 0  not null,
    po00415 varchar(3)    default '' not null,
    po00416 tinyint       default 0  not null,
    po00417 varchar(19)   default '' not null,
    po00418 varchar(40)   default '' not null
        primary key,
    po00419 tinyint       default 0  not null,
    po00420 tinyint       default 0  not null,
    po00421 varchar(40)              not null
);

create table sdpo006
(
    po00601 varchar(40)             not null
        primary key,
    po00602 varchar(19)             not null,
    po00603 varchar(40)             not null,
    po00604 varchar(200) default '' not null,
    po00605 varchar(5000)           not null,
    po00606 int          default 0  not null,
    po00607 int          default 0  not null,
    po00608 varchar(500) default '' not null,
    po00609 varchar(500) default '' not null,
    po00610 varchar(40)  default '' not null,
    po00611 varchar(20)  default '' not null,
    po00612 varchar(500) default '' not null
);

create table sdpo007
(
    po00701 varchar(40)   not null,
    po00702 varchar(40)   not null,
    po00703 varchar(40)   not null,
    po00704 varchar(40)   not null,
    po00705 varchar(19)   not null,
    po00706 varchar(300)  not null,
    po00707 varchar(19)   not null,
    po00708 int           not null,
    po00709 varchar(19)   not null,
    po00710 int           not null,
    po00711 int           not null,
    po00712 int default 1 not null,
    primary key (po00701, po00702)
);

create table sdpo009
(
    po00901 varchar(40)             not null
        primary key,
    po00902 varchar(8)              not null,
    po00903 varchar(40)             not null,
    po00904 varchar(100)            not null,
    po00905 varchar(80)             not null,
    po00906 varchar(200)            not null,
    po00907 varchar(40)             not null,
    po00908 varchar(40)             not null,
    po00909 blob null,
    po00910 varchar(19)             not null,
    po00911 varchar(10)             not null,
    po00912 varchar(19)             not null,
    po00913 int          default 0  not null,
    po00914 varchar(40)             not null,
    po00915 varchar(500)            not null,
    po00916 varchar(40)  default '' not null,
    po00917 varchar(40)  default '' not null,
    po00918 varchar(100) default '' not null,
    po00919 int          default 0  not null,
    po00920 tinyint      default 0  not null,
    po00921 int          default 0  not null,
    po00922 varchar(20)  default '' not null
);

create table sdpo010
(
    po01001 varchar(40) not null,
    po01002 varchar(40) not null,
    primary key (po01001, po01002)
);

create table sdpo011
(
    po01101 varchar(8)             not null
        primary key,
    po01102 varchar(200)           not null,
    po01103 varchar(8)             not null,
    po01104 varchar(80)            not null,
    po01105 varchar(40)            not null,
    po01106 varchar(40)            not null,
    po01107 varchar(40)            not null,
    po01108 varchar(40) default '' not null,
    po01109 tinyint     default 0  not null
);

create table sdpo012
(
    po01201 varchar(40)       not null
        primary key,
    po01202 varchar(8)        not null,
    po01203 varchar(100)      not null,
    po01204 blob              not null,
    po01205 tinyint default 0 not null,
    po01206 int     default 0 not null,
    po01207 text null
);

create table sdpo013
(
    po01301 varchar(8)   not null
        primary key,
    po01302 varchar(100) not null,
    po01303 varchar(8)   not null,
    po01304 varchar(80)  not null
);

create table sdpo014
(
    po01401 varchar(50)             not null
        primary key,
    po01402 varchar(200) default '' not null,
    po01403 varchar(50)             not null,
    po01404 varchar(500)            not null,
    po01405 varchar(50)  default '' not null
);

create table sdpo020
(
    po02001 varchar(40)               not null
        primary key,
    po02002 varchar(40)               not null,
    po02003 int                       not null,
    po02004 varchar(100)              not null,
    po02005 tinyint                   not null,
    po02006 int                       not null,
    po02007 tinyint       default 0   not null,
    po02008 int           default 0   not null,
    po02009 tinyint       default 0   not null,
    po02010 tinyint       default 0   not null,
    po02011 int           default 0   not null,
    po02012 tinyint       default 1   not null,
    po02013 int           default 999 not null,
    po02014 int           default 0   not null,
    po02015 int           default 0   not null,
    po02016 int           default 0   not null,
    po02017 varchar(40)   default ''  not null,
    po02018 int           default 0   not null,
    po02019 varchar(2000) default ''  not null,
    po02021 tinyint       default 0   not null
);

create table sdpo020_condition
(
    po020_Condition01 varchar(40)              not null
        primary key,
    po020_Condition02 varchar(40)              not null,
    po020_Condition03 varchar(40)              not null,
    po020_Condition04 varchar(40)   default '' not null,
    po020_Condition05 varchar(40)   default '' not null,
    po020_Condition06 varchar(40)              not null,
    po020_Condition07 varchar(40)   default '' not null,
    po020_Condition08 varchar(1000) default '' not null,
    po020_Condition09 varchar(40)   default '' not null,
    po020_Condition10 varchar(40)   default '' not null,
    po020_Condition11 varchar(40)   default '' not null,
    po020_Condition12 varchar(40)   default '' not null,
    po020_Condition13 varchar(40)   default '' not null,
    po020_Condition14 varchar(1000) default '' not null,
    po020_Condition15 int           default 0  not null
);

create table sdpo020_relation
(
    po020_Relation01 varchar(40)            not null
        primary key,
    po020_Relation02 varchar(40)            not null,
    po020_Relation03 varchar(40) default '' not null,
    po020_Relation04 varchar(40) default '' not null,
    po020_Relation05 int         default 1  not null
);

create table sdpo020b
(
    po020b01 varchar(40)               not null,
    po020b02 varchar(40)               not null,
    po020b03 varchar(167)              not null,
    po020b04 varchar(100)              not null,
    po020b05 tinyint                   not null,
    po020b06 int                       not null,
    po020b07 tinyint       default 0   not null,
    po020b08 int           default 0   not null,
    po020b09 varchar(400)  default ''  not null,
    po020b10 tinyint       default 0   not null,
    po020b11 tinyint       default 0   not null,
    po020b12 int           default 0   not null,
    po020b13 tinyint       default 1   not null,
    po020b14 int           default 999 not null,
    po020b15 int                       not null,
    po020b16 int                       not null,
    po020b17 int           default 0   not null,
    po020b18 varchar(40)   default ''  not null,
    po020b19 int           default 0   not null,
    po020b20 varchar(2000) default ''  not null,
    po020b26 tinyint       default 0   not null,
    primary key (po020b01, po020b02)
);

create table sdpo020b_condition
(
    po020_Condition01 varchar(40)              not null,
    po020_Condition02 varchar(40)              not null,
    po020_Condition03 varchar(40)              not null,
    po020_Condition04 varchar(40)   default '' not null,
    po020_Condition05 varchar(40)   default '' not null,
    po020_Condition06 varchar(40)              not null,
    po020_Condition07 varchar(40)   default '' not null,
    po020_Condition08 varchar(1000) default '' not null,
    po020_Condition09 varchar(40)   default '' not null,
    po020_Condition10 varchar(40)   default '' not null,
    po020_Condition11 varchar(40)   default '' not null,
    po020_Condition12 varchar(40)   default '' not null,
    po020_Condition13 varchar(40)   default '' not null,
    po020_Condition14 varchar(1000) default '' not null,
    po020_Condition15 varchar(40)              not null,
    po020_Condition16 int           default 0  not null,
    primary key (po020_Condition01, po020_Condition15)
);

create table sdpo020b_history
(
    po020b01 varchar(40)               not null,
    po020b02 varchar(40)               not null,
    po020b03 int                       not null,
    po020b04 varchar(100)              not null,
    po020b05 tinyint                   not null,
    po020b06 int                       not null,
    po020b07 tinyint       default 0   not null,
    po020b08 int           default 0   not null,
    po020b09 varchar(128)  default ''  not null,
    po020b10 tinyint       default 0   not null,
    po020b11 tinyint       default 0   not null,
    po020b12 int           default 0   not null,
    po020b13 tinyint       default 1   not null,
    po020b14 int           default 999 not null,
    po020b15 int                       not null,
    po020b16 int                       not null,
    po020b17 int           default 0   not null,
    po020b18 varchar(40)   default ''  not null,
    po020b19 int           default 0   not null,
    po020b20 varchar(2000) default ''  not null,
    po020b26 tinyint       default 0   not null,
    primary key (po020b01, po020b02)
);

create table sdpo020b_instance
(
    po020b01 varchar(40)               not null,
    po020b02 varchar(40)               not null,
    po020b03 int                       not null,
    po020b04 varchar(100)              not null,
    po020b05 tinyint                   not null,
    po020b06 int                       not null,
    po020b07 tinyint       default 0   not null,
    po020b08 int           default 0   not null,
    po020b09 varchar(128)  default ''  not null,
    po020b10 tinyint       default 0   not null,
    po020b11 tinyint       default 0   not null,
    po020b12 int           default 0   not null,
    po020b13 tinyint       default 1   not null,
    po020b14 int           default 999 not null,
    po020b15 int                       not null,
    po020b16 int                       not null,
    po020b17 int           default 0   not null,
    po020b18 varchar(40)   default ''  not null,
    po020b19 int           default 0   not null,
    po020b20 varchar(2000) default ''  not null,
    po020b26 tinyint       default 0   not null,
    primary key (po020b01, po020b02)
);

create table sdpo020b_instance_history
(
    po020b01 varchar(40)               not null,
    po020b02 varchar(40)               not null,
    po020b03 int                       not null,
    po020b04 varchar(100)              not null,
    po020b05 tinyint                   not null,
    po020b06 int                       not null,
    po020b07 tinyint       default 0   not null,
    po020b08 int           default 0   not null,
    po020b09 varchar(128)  default ''  not null,
    po020b10 tinyint       default 0   not null,
    po020b11 tinyint       default 0   not null,
    po020b12 int           default 0   not null,
    po020b13 tinyint       default 1   not null,
    po020b14 int           default 999 not null,
    po020b15 int                       not null,
    po020b16 int                       not null,
    po020b17 int           default 0   not null,
    po020b18 varchar(40)   default ''  not null,
    po020b19 int           default 0   not null,
    po020b20 varchar(2000) default ''  not null,
    po020b26 tinyint       default 0   not null,
    primary key (po020b01, po020b02)
);

create table sdpo020b_relation
(
    po020_Relation01 varchar(40)            not null,
    po020_Relation02 varchar(40)            not null,
    po020_Relation03 varchar(40) default '' not null,
    po020_Relation04 varchar(40) default '' not null,
    po020_Relation05 int         default 1  not null,
    po020_Relation06 varchar(40)            not null,
    primary key (po020_Relation01, po020_Relation06)
);

create table sdpo020b_relation_history
(
    po020_Relation01 varchar(40)            not null,
    po020_Relation02 varchar(40)            not null,
    po020_Relation03 varchar(40) default '' not null,
    po020_Relation04 varchar(40) default '' not null,
    po020_Relation05 int         default 1  not null,
    po020_Relation06 varchar(40)            not null,
    primary key (po020_Relation01, po020_Relation06)
);

create table sdpo027
(
    po02701 varchar(50) not null,
    po02702 varchar(50) not null,
    primary key (po02701, po02702)
);

create table sdpo028
(
    po02801 varchar(8)   not null
        primary key,
    po02802 varchar(100) not null,
    po02803 varchar(8)   not null,
    po02804 varchar(80)  not null
);

create table sdpo032
(
    po03201 varchar(40)   not null,
    po03202 varchar(40)   not null,
    po03203 int default 0 not null,
    po03204 tinyint       not null,
    primary key (po03201, po03202)
);

create table sdpo057
(
    po05701 varchar(40)                     not null
        primary key,
    po05702 varchar(200)                    not null,
    po05703 varchar(40)                     not null,
    po05704 varchar(40)                     not null,
    po05705 varchar(20)                     not null,
    po05706 int                             not null,
    po05707 varchar(40)                     not null,
    po05708 varchar(40)                     not null,
    po05709 varchar(500)                    not null,
    po05710 varchar(40)                     not null,
    po05711 varchar(40)                     not null,
    po05712 tinyint                         not null,
    po05713 varchar(40)                     not null,
    po05714 varchar(20)                     not null,
    po05715 decimal(18, 6) default 0.000000 not null,
    po05716 varchar(40)    default ''       not null
);

create table sdpo100
(
    po10001 varchar(40)       not null
        primary key,
    po10002 varchar(10)       not null,
    po10003 varchar(40)       not null,
    po10004 varchar(20)       not null,
    po10005 varchar(100)      not null,
    po10006 varchar(3000)     not null,
    po10007 varchar(10)       not null,
    po10008 varchar(40)       not null,
    po10009 tinyint           not null,
    po10010 varchar(100)      not null,
    po10011 varchar(40)       not null,
    po10012 tinyint default 0 not null
);

create table sdpo200
(
    po20001 varchar(8)             not null
        primary key,
    po20002 varchar(100)           not null,
    po20003 varchar(8)             not null,
    po20004 varchar(80)            not null,
    po20005 varchar(40)            not null,
    po20006 varchar(30) default '' not null
);

create table sdpo201
(
    po20101 varchar(40)    not null
        primary key,
    po20102 varchar(100)   not null,
    po20103 varchar(80)    not null,
    po20104 varchar(50)    not null,
    po20105 varchar(20)    not null,
    po20106 varchar(100)   not null,
    po20107 varchar(100)   not null,
    po20108 varchar(8)     not null,
    po20109 varchar(8)     not null,
    po20110 decimal(18, 6) not null,
    po20111 decimal(18, 6) not null,
    po20112 decimal(18, 6) not null
);

create table sdpo202
(
    po20201 varchar(40)  not null
        primary key,
    po20202 varchar(40)  not null,
    po20203 varchar(10)  not null,
    po20204 varchar(40)  not null,
    po20205 varchar(500) not null,
    po20206 varchar(40)  not null,
    po20207 varchar(19)  not null,
    po20208 tinyint      not null,
    po20209 varchar(40)  not null,
    po20210 varchar(10)  not null
);

create table sdpo203
(
    po20301 varchar(40)                     not null
        primary key,
    po20302 varchar(40)                     not null,
    po20303 varchar(40)                     not null,
    po20304 smallint       default 0        not null,
    po20305 varchar(40)                     not null,
    po20306 varchar(40)                     not null,
    po20307 decimal(18, 6)                  not null,
    po20308 decimal(18, 6)                  not null,
    po20309 decimal(18, 6)                  not null,
    po20310 varchar(500)   default ''       not null,
    po20311 decimal(18, 6) default 0.000000 not null
);

create table sdpo204
(
    po20401 varchar(40)  not null
        primary key,
    po20402 varchar(40)  not null,
    po20403 varchar(10)  not null,
    po20404 varchar(40)  not null,
    po20405 varchar(40)  not null,
    po20406 varchar(500) not null,
    po20407 varchar(40)  not null,
    po20408 varchar(19)  not null,
    po20409 tinyint      not null,
    po20410 varchar(40)  not null,
    po20411 varchar(10)  not null,
    po20412 tinyint      not null
);

create table sdpo205
(
    po20501 varchar(40)    not null
        primary key,
    po20502 varchar(40)    not null,
    po20503 varchar(40)    not null,
    po20504 smallint       not null,
    po20505 varchar(40)    not null,
    po20506 varchar(40)    not null,
    po20507 decimal(18, 6) not null,
    po20508 decimal(18, 6) not null,
    po20509 decimal(18, 6) not null,
    po20510 varchar(500)   not null
);

create table sdps002
(
    ps00201 varchar(40)            not null
        primary key,
    ps00202 varchar(100)           not null,
    ps00203 varchar(255)           not null,
    ps00204 tinyint                not null,
    ps00205 varchar(8)             not null,
    ps00206 varchar(6000)          not null,
    ps00207 varchar(10) default '' not null,
    ps00208 varchar(10) default '' not null
);

create table sdpzexporthis
(
    PzExportHis01 varchar(40) not null
        primary key,
    PzExportHis02 varchar(19) not null
);

create table sdrt001
(
    rt00101 varchar(40) not null
        primary key,
    rt00102 varchar(20) not null,
    rt00103 tinyint     not null,
    rt00104 text null,
    rt00105 varchar(100) null,
    rt00106 varchar(40) null,
    rt00107 varchar(20) null,
    rt00108 varchar(20) null
);

create table staff_addition_info
(
    id          varchar(50) not null
        primary key,
    staff_id    varchar(255) null,
    system_id   varchar(255) null,
    system_name varchar(255) null,
    wx_user_id  varchar(255) null,
    wx_open_id  varchar(255) null
);

create table sys_job
(
    job_id          bigint auto_increment
        primary key,
    job_name        varchar(64)  default '' null,
    job_group       varchar(64)  default 'DEFAULT' null,
    invoke_target   varchar(500) not null,
    cron_expression varchar(255) default '' null,
    misfire_policy  varchar(20)  default '3' null,
    concurrent      char         default '1' null,
    status          char         default '0' null,
    create_by       varchar(64)  default '' null,
    create_time     datetime null,
    update_by       varchar(64)  default '' null,
    update_time     datetime null,
    remark          varchar(500) default '' null
);

create table sys_job_log
(
    job_log_id     bigint auto_increment
        primary key,
    job_name       varchar(64)  not null,
    job_group      varchar(64)  not null,
    invoke_target  varchar(500) not null,
    job_message    varchar(500) null,
    status         char          default '0' null,
    exception_info varchar(2000) default '' null,
    create_time    datetime null
);

create table system_config
(
    id     int null,
    name   varchar(100) null,
    coding varchar(20) null,
    parent int null,
    value  varchar(2000) null,
    remark varchar(2000) null
);

create table system_log
(
    id        int auto_increment
        primary key,
    type      varchar(100) null,
    datetime  varchar(20) null,
    url       varchar(8000) null,
    user_id   varchar(40) null,
    user_name varchar(100) null,
    params    text null,
    ip        varchar(100) null,
    method    varchar(20) null,
    title     varchar(200) null,
    result    longtext null
);

create table travel_application
(
    id             int auto_increment
        primary key,
    traveller      varchar(50) null,
    position       varchar(50) null,
    department     varchar(50) null,
    start_time     datetime null,
    end_time       datetime null,
    create_time    datetime null,
    total_time     varchar(50) null,
    travel_type    int null,
    transportation varchar(50) null,
    place          varchar(100) null,
    remark         varchar(500) null,
    status         int default 0 null,
    travellerId    varchar(50) null,
    departmentId   varchar(50) null,
    travel_fee     decimal(10, 2) null,
    stay_fee       decimal(10, 2) null,
    other_fee      decimal(10, 2) null,
    total_fee      decimal(10, 2) null,
    uuid           varchar(40) not null,
    constraint travel_application_id_uindex
        unique (id),
    constraint travel_application_uuid_uindex
        unique (uuid)
);



create table t_catalogue
(
    id     int auto_increment primary key,
    name   varchar(50),
    parent varchar(50),
    sort   int
);

create table t_certificate
(
    id               bigint auto_increment primary key,
    serial_number    varchar(80),
    name             varchar(70),
    file_url         varchar(200),
    gain_time        datetime,
    expiration_time  datetime,
    upload_user_id   bigint,
    upload_user_name varchar(50),
    upload_time      datetime,
    update_user_id   bigint,
    update_user_name varchar(50),
    update_time      datetime,
    state            int,
    catalogue_id     int,
    push_msg         int,
    subsidy          decimal(10, 2),
    holder           varchar(40)
);

create table t_download_record
(
    id               bigint auto_increment primary key,
    name             varchar(50),
    record_user_id   bigint,
    record_user_name varchar(50),
    record_time      datetime,
    record_text      text,
    certificate_id   bigint
);


create table t_authorization
(
    id             bigint auto_increment primary key,
    staff_id       varchar(50),
    certificate_id bigint
);

create table reptile
(
    id               varchar(40) not null primary key,
    title            varchar(1000),
    content          varchar(8000),
    datetime         varchar(20),
    reptile_datetime varchar(40),
    `keys`           varchar(200),
    source_name      varchar(100)
);

create table staff_card
(
    id           varchar(40) NOT NULL primary key,
    name         varchar(40),
    phone        varchar(20),
    tel          varchar(20),
    tel2         varchar(20),
    tel3         varchar(20),
    address      varchar(200),
    phone2       varchar(20),
    log          varchar(1000),
    email        varchar(100),
    fax          varchar(100),
    company      varchar(200),
    email_number varchar(20)
);

create table project_auth
(
    id         int auto_increment,
    project_id varchar(40) null,
    staff_id   varchar(40) null,
    constraint project_auth_pk
        primary key (id)
);

create table term
(
    id               varchar(40) not null
        primary key,
    concat_id        varchar(40) null comment '合同id',
    name             varchar(200) null comment '费用名称',
    start_date       date null comment '开始日期',
    end_date         date null comment '截止日期',
    type             varchar(200) null comment '计费方式',
    unit             varchar(50) null comment '计费单位',
    pay_cycle        varchar(50) null comment '支付周期',
    month_bill       tinyint null comment '是否月度账单',
    pay_type         varchar(50) null comment '付款方式',
    pay_day          varchar(50) null comment '付款日',
    first_start_date date null comment '首次计费开始日期',
    first_end_date   date null comment '首次计费结束日期',
    first_money      decimal(10, 2) null comment '首次计费金额',
    price_type       varchar(50) null comment '计价方式',
    money            decimal(10, 2) null,
    price            decimal(10, 2) null
);
create table concat_bill
(
    id            varchar(40) not null
        primary key,
    concat_id     varchar(40) null,
    name          varchar(200) null comment '费用名称',
    start_date    date null comment '账单开始日期',
    end_date      date null comment '账单截止日期',
    type          varchar(50) null comment '计费方式',
    unit          varchar(50) null comment '计费单位',
    pay_cycle     varchar(50) null comment '支付周期',
    month_bill    tinyint null comment '是否月度账单',
    pay_type      varchar(50) null comment '支付类型',
    pay_end_date  date null comment '支付截止日期',
    state         varchar(50) null comment '账单状态',
    arrearage_day int null comment '欠费天数',
    room          varchar(50) null comment '铺位号',
    floor         varchar(50) null comment '楼层',
    brand         varchar(200) null comment '品牌',
    money         decimal(10, 2) null comment '应收金额',
    sj_money      decimal(10, 2) null comment '实际金额',
    pay_money     decimal(10, 2) null comment '已收金额',
    arrearage     decimal(10, 2) null comment '欠费金额',
    concat_type   varchar(50) null comment '合同类型',
    approve_state tinyint null comment '状态：0=正常，1=已审核，2=作废',
    invoice_state int null comment '开票状态：0=为开票，1=已开票',
    datetime      datetime null comment '账单生成日期',
    staff_id      varchar(40) null comment '单据添加人',
    source_id     varchar(40) null comment '来源id',
    back_money    decimal(10, 2) null
);

create table contract_word_model
(
    id         int auto_increment
        primary key,
    name       varchar(250) null,
    rich_text  text null,
    type       int null,
    params_arr text null,
    constraint contract_word_model_id_uindex
        unique (id)
);

create table contract_word_model_params
(
    id        int auto_increment
        primary key,
    name      varchar(250) null,
    mark_Name varchar(250) null,
    type      int null,
    constraint contract_word_model_params_id_uindex
        unique (id)
);

create table place_use_contract
(
    id             varchar(40),
    contract_name  varchar(250),
    money          decimal(10, 2),
    price          decimal(10, 2),
    type           int,
    bond           decimal(10, 2),
    electric_price varchar(200),
    end_date       varchar(20),
    pay_cycle      int,
    place_area     decimal(10, 2),
    place_num      varchar(250),
    place_use_for  varchar(250),
    start_date     varchar(20),
    tax_rate       decimal(10, 2),
    water_price    decimal(10, 2),
    create_date    varchar(20),
    record_staff   varchar(40),
    part_b         varchar(40),
    electric_type  int,
    files          text
);

create table advert_place_contract
(
    id                            varchar(40) not null primary key,
    contract_name                 varchar(250),
    number                        int,
    place_num                     varchar(200),
    place_address                 varchar(250),
    advert_type                   varchar(250),
    start_date                    varchar(20),
    end_date                      varchar(20),
    price                         decimal(10, 2),
    money                         decimal(10, 2),
    capitalization_money          varchar(100),
    design_price                  decimal(10, 2),
    per_electric_price            decimal(10, 2),
    electric_money                decimal(10, 2),
    capitalization_electric_money varchar(100),
    create_date                   varchar(20),
    update_date                   varchar(20),
    type                          int,
    part_b                        varchar(250),
    update_staff                  varchar(40),
    record_staff                  varchar(40),
    files                         text
);

insert into sdeb003 (eb00301, eb00302, eb00303, eb00304, eb00305, eb00306, eb00307, eb00308, eb00309, eb00310, eb00311,
                     eb00312, eb00313, eb00314, eb00315, eb00316, eb00317, eb00318, eb00319, eb00320)
values (N'1010100', N'00', N'10101', N'项目立项', 3, 0, N'102012', 1, 1, N'', 1, 0, 0, N'00', N'1010100', 0, 0, 1, 0,
        N''),
       ( '19999054', '13', '10210', '广告位租赁', 3, 0, '1320289'
       , 1, 1, '', 1, 0, 0, 3, 1000204, 0, 0, 1, 0, ''),
       (N'1020301', N'01', N'10203', N'部门组织', 3, 0, N'10190', 1, 0, N'', 0, 0, 0, N'01', N'1030301', 0, 0, 0, 0,
        N''),
       (N'1020302', N'02', N'10203', N'职务定义', 3, 0, N'10152', 1, 0, N'', 0, 0, 0, N'02', N'1030302', 0, 0, 0, 0,
        N''),
       (N'1020303', N'03', N'10203', N'员工资料维护', 3, 0, N'10154', 1, 0, N'', 0, 0, 0, N'03', N'1030303', 0, 0, 0, 0,
        N''),
       (N'1050124', N'24', N'10501', N'材料计划单', 3, 0, N'13270', 1, 1, N'', 1, 0, 0, N'24', N'1020124', 0, 0, 1, 0,
        N''),
       ('12999376', '13', '10210', '场地合同-多经', 3, 0, '1320288', 1, 1, '', 1, 0, 0, 3, 1000204, 0, 0, 1, 0, ''),
       (N'1050201', N'01', N'10502', N'采购订单', 3, 0, N'15306', 1, 1, N'', 1, 0, 0, N'05', N'1020205', 0, 0, 1, 0,
        N''),
       (N'1050207', N'07', N'10502', N'申请单', 3, 0, N'15304', 1, 1, N'', 1, 0, 0, N'04', N'1020204', 0, 0, 1, 0, N''),
       (N'1050208', N'08', N'10502', N'采购收货入库单', 3, 0, N'15323', 1, 1, N'', 1, 0, 0, N'09', N'1020209', 0, 0, 1,
        0, N''),
       (N'1050229', N'29', N'10502', N'采购合同', 3, 0, N'10564', 1, 1, N'', 1, 0, 0, N'29', N'1020229', 0, 0, 1, 0,
        N''),
       (N'1050231', N'31', N'10502', N'合同付款', 3, 0, N'10563', 1, 1, N'', 1, 0, 0, N'31', N'1020231', 0, 0, 1, 0,
        N''),

       (N'1050302', N'02', N'10503', N'项目领料出库', 3, 0, N'15313', 1, 1, N'', 1, 0, 0, N'02', N'1020302', 0, 0, 1, 0,
        N''),
       (N'1050303', N'03', N'10503', N'项目退料单', 3, 0, N'15321', 1, 1, N'', 1, 0, 0, N'03', N'1020303', 0, 0, 1, 0,
        N''),
       (N'1050311', N'11', N'10503', N'库存盘点单', 3, 11, N'15309', 1, 0, N'', 1, 0, 0, N'11', N'1020311', 0, 0, 1, 0,
        N''),

       (N'12403050', N'4', N'12404', N'运营资产明细登记', 3, 0, N'1320273', 1, 1, N'', 1, 0, 0, N'3', N'1000203', 0, 0,
        1, 0, N''),
       (N'12403051', N'13', N'10210', N'出差申请单', 3, 0, N'1320274', 1, 1, N'', 1, 0, 0, N'3', N'1000203', 0, 0, 1, 0,
        N''),
       (N'12403052', N'13', N'10210', N'开票申请单', 3, 0, N'1320275', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1, 0,
        N''),
       (N'12403053', N'13', N'10210', N'我的加班', 3, 0, N'1320276', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1, 0,
        N''),
       (N'12403054', N'13', N'10210', N'用车申请单', 3, 0, N'1320277', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1, 0,
        N''),
       (N'12403055', N'13', N'10210', N'投标盖章申请', 3, 0, N'1320278', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1,
        0, N''),
       (N'12403056', N'13', N'10210', N'投标结果反馈', 3, 0, N'13202782', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1,
        0, N''),
       (N'12403057', N'13', N'10210', N'就餐申请', 3, 0, N'1320280', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1, 0,
        N''),
       (N'12403058', N'13', N'10210', N'租赁合同管理', 3, 0, N'1320287', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1,
        0, N''),

       (N'1240320', N'20', N'12403', N'我的请假', 3, 20, N'137214', 1, 1, N'', 1, 0, 0, N'20', N'1000220', 0, 0, 1, 0,
        N''),
       (N'12403357', N'13', N'10210', N'采购付款申请', 3, 0, N'132029', 1, 1, N'proOtherPay', 1, 0, 0, N'3', N'1000204',
        0, 0, 1, 0, N''),

       (N'12403365', N'13', N'10210', N'销售合同登记', 3, 0, N'132027', 1, 1, N'SalesContract', 1, 0, 0, N'3',
        N'1000204', 0, 0, 1, 0, N''),
       (N'12403375', N'13', N'10210', N'智能化工程付款申请', 3, 0, N'1320281', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0,
        0, 1, 0, N''),
       (N'12403376', N'13', N'10210', N'分包合同登记', 3, 0, N'1320282', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1,
        0, N''),
       (N'12403377', N'13', N'10210', N'固定资产采购申请', 3, 0, N'1320283', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0,
        1, 0, N''),


       (N'1240505', N'05', N'12405', N'办公用品领用', 3, 0, N'15223', 1, 1, N'', 1, 0, 0, N'05', N'1000505', 0, 0, 1, 0,
        N''),

       (N'12412375', N'13', N'10210', N'招待申请', 3, 0, N'1320286', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1, 0,
        N''),

       (N'1293365', N'13', N'10210', N'打分审批', 3, 0, N'137212', 1, 1, N'', 1, 0, 0, N'3', N'1000204', 0, 0, 1, 0,
        N'');


insert into sdpj004 (pj00401, pj00402, pj00403, pj00404, pj00405, pj00406, pj00407, pj00408, pj00409, pj00410, pj00411,
                     pj00412, pj00413, pj00414, pj00415, pj00416, pj00417, pj00418, pj00419, pj00420, pj00421, pj00422,
                     pj00423, pj00424, pj00425, pj00426, pj00427, pj00428, pj00429, pj00430, pj00431, pj00432, pj00433,
                     pj00434, pj00435, pj00436, pj00437, pj00438, pj00439, pj00440, pj00441, pj00442, pj00443, pj00444,
                     pj00445, pj00446, pj00447, pj00448, pj00449, pj00450, pj00451, pj00452, pj00453, pj00454, pj00455,
                     pj00456, pj00457)
values ('07540078-07fa-4aa5-89ae-22b8f0f9f792', '1001', 0, 'Unknown', 'Unknown', '2021-09-11', '', '', '', '本科',
        '2023-12-21 08:46:40', 1, '', '', '', '', 'DUIW2LO0', 0.000000, 0, '18306291043', '1001', '', '', '2021-09-11',
        '2021-09-11', 0, '', 0, 0, '2021-09-11', 0, '2021-09-11', '0', '', '', '2021-09-11', '', '', '', 0,
        '963398090@qq.com', '', '0', '0', '', '0', 0, 0, '', '', null, null, null, null, null, null, null);

insert into sdpj005 (pj00501, pj00502, pj00503, pj00504)
values ('12345678', '管理员', ' ', ' ');

insert into sdpj006 (pj00601, pj00602)
values ('12345678', '07540078-07fa-4aa5-89ae-22b8f0f9f792');

insert into sdey003 (ey00301, ey00302, ey00303, ey00304, ey00305, ey00306, ey00307, ey00308, ey00309, ey00310, ey00311,
                     ey00312, ey00313, ey00314, ey00315, ey00316, ey00317, ey00318, ey00319, ey00320, ey00321)
values ('1001', '', '', 0, 'QDEyM0AxMjM=', 0, '2099-01-01', 0, '', '5:16:56', '5:16:56', '[2019-08-24],[192.168.3.252]',
        '1001', 0, '', 1, 0, '', 0, '', '');

insert into pro_role (id, menu_id, role_id)
values ('1', 'c3b9343c-527c-42f1-8c6e-6e82488fee67', '12345678');

insert into pro_menu (id, name, url, parent, sort, remark, ico, type, coding, is_Outer, outer_path, hide, bean_name,
                      flow_sql)
values ('0060d87c-332e-4675-8458-799e02816a61', '固定资产统计', 'fixedAssets/list',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 17, null, 'mdi-book-edit', 3, null, 0, null, 0, null, null),
       ('0091a85d-feac-435a-93c9-34386827c023', '项目材料分析V2', 'project/proUseMsgV2',
        '3a50d43b-4e11-4036-a378-464a024353ce', 99, null, null, 3, '', 0, null, 0, null, null),
       ('013b23ef-b88d-46d8-87ea-15a3bf3bac3d', '材料计划单', 'project/plan', '8731ebf3-ec22-4978-b64c-bc0b5d6fe4d9', 1,
        null, '', null, null, null, null, null, null, null),
       ('022abee0-9900-4359-99bd-2513756cddfe', '采购付款申请', 'otherPay/pro', 'a59d92e0-9f04-449a-b942-6acf6704f9f0',
        99, null, 'mdi-contactless-payment-circle', 3, '132029', 0, null, 0, 'proOtherPay', 'select * from(
select id,staff_id,pay_money,
isnull(sdpj003.pj00302,'''''''') as Department,
isnull(sdpj004.pj00402,'''''''') as Personnel,
OAState=isnull((select po00308 from sdpo003 where po00307=id),0)

from pro_other_pay
left join sdpj004 on staff_id=pj00401
left join sdpj003 on pj00417=pj00301
)#Temp Where 1=1'),
       ('038a57f2-eea7-42da-8e5a-39582203a75b', '设备维修付款', 'otherPay/weixiu',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 98, null, 'mdi-contactless-payment-circle', 3, null, 0, null, 0, '',
        null),
       ('06058a9d-445d-495d-bafa-ef1f739d1469', '采购管理', 'procurement/noDhList', '', 7, '', 'mdi-cart-minus ', 3, '',
        0, null, null, null, null),
       ('07341971-8367-478f-9418-80f2a2615f08', '每日消费汇总', 'account/diningDayStatistics',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 12, null, 'mdi-history', 3, null, 0, null, null, null, null),
       ('08e01021-41c0-4537-b191-37e46636bde1', '办公用品库存查询', 'workMaterial/material',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 99, null, 'mdi-clock-check', 3, null, 0, null, null, null, null),
       ('0bfb09fd-766b-4131-bb2f-00d64cad619d', '报表管理', '', '', 6, null, '', null, null, null, null, null, null,
        null),
       ('0c0de993-f189-4409-8ed6-876a92cc89ac', '个人餐别统计', 'account/diningPersonalStatistics',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 13, null, 'mdi-history', 3, null, 0, null, null, null, null),
       ('0c9e1490-ce61-4006-bd91-f75952ed65c1', '维修统计报表', 'projectAfter/report',
        '9e755b32-b3d2-4e95-ba42-81476e55b062', 3, null, 'mdi-finance', 3, null, 0, null, null, null, null),
       ('10a8191f-1e0d-478f-adbd-494ea2ed82f3', '采购最多供应单位分析', '/managent/getProCompanyMax',
        '0bfb09fd-766b-4131-bb2f-00d64cad619d', 66, null, '', null, null, null, null, null, null, null),
       ('11ecdef4-939b-46e2-8599-589465d7ad9d', '智能化工程付款申请', 'proSubcontractPay/list/list',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 6, 'article/znh-pay', 'mdi-contactless-payment-circle', 3, '1320281', 0,
        '', null, null, 'select * from(
select id,staff,pay_money,
isnull(sdpj003.pj00302,'''''''') as Department,
isnull(sdpj004.pj00402,'''''''') as Personnel,
OAState=isnull((select po00308 from sdpo003 where po00307=id),0)

from pro_subcontract_pay
left join sdpj004 on staff=pj00401
left join sdpj003 on pj00417=pj00301
)#Temp Where 1=1'),
       ('15306651-9c5e-4406-ba2c-e763577d21c7', '在线用户', 'user/online-user', '3e1e0753-3960-4f24-a53f-3b9a2cbca98d',
        1, null, 'mdi-account-group', 3, null, 0, null, null, null, null),
       ('17ffd135-e06f-47a2-9f39-37f06a494cf1', '我的文档', 'article/index/:folderId?',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 3, null, 'mdi-book-edit', 3, '15203', 0, null, null, null, 'select * from(
select po00901,po00902,po00903,po00904,po00905,po00906
,po00907,po00908,po00909=convert(image,NULL),po00910,po00911,
po00912,po00913,po00914,po00915,IsUpdated=convert(tinyint,0),
isnull(sdpj003.pj00302,'''') as Department,
MenuName=isnull((select po01102 from sdpo011 where po01101=po00902),''''),
isnull(sdpj004.pj00402,'''') as Personnel,
po00916,pa00102=isnull(pa00102,''''),po00917,pa00303=isnull(pa00303,''''),
po00918=isnull(po00918,''''),po00919,po00920,po00921,po00922,
po01102,po01105,po01106,po01108,
po01109,po01203=ISNULL(po01203,''''),
OAState=isnull((select po00308 from sdpo003 where po00307=po00901),0)

from sdpo009
inner join sdpo011 on po01101=po00902
left join sdpo012 on po01201=po01105
left join sdpj003 on po00907=pj00301
left join sdpj004 on po00908=pj00401
left join sdpa001 on pa00101=po00916
left join sdpa003 on pa00302=po00917
)#Temp Where 1=1 '),
       ('186d3392-4805-4eca-a7bd-43c8720c6fbb', '收款管理', 'salesContract/list/CollectionDetail',
        'e7afc22c-d4fd-4846-ace0-c47bd086fe98', 2, null, 'mdi-book', 3, null, 0, null, null, null, null),
       ('1c2c6863-262d-44a1-8ea0-a1742d80b875', '供应商采购记录', 'company/proHistory/:companyId?/:companyName?',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 9, null, 'mdi-history', 3, null, 0,
        '/managent/getPage?pageName=company/companyProMater', null, null, null),
       ('1ddf1cb6-e041-42b3-9162-f5b011f32b71', '公司公告', 'personnel/infoList',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 8, null, 'mdi-bullhorn', 3, null, 0, null, null, null, null),
       ('1eaddcfb-ea3b-4e70-a8a8-062686953b75', '已过期证书', 'certificate/overdue',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 13, null, 'mdi-book-cancel', 3, null, 0, null, 1, null, null),
       ('2197f68a-bd89-42dc-b322-aa16e9c321cc', '临近过期证书', 'certificate/nearOverdue',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 14, null, 'mdi-book-clock-outline', 3, null, 0, null, 1, null, null),
       ('229e5f8e-a25b-4080-b54c-2bd7bd2ae59c', '供应商入库记录', 'company/putHistory',
        '8c9d5705-6cf9-4153-9153-e8f73462656f', 7, null, 'mdi-clock-check', 3, null, 0, null, 0, null, null),
       ('2337d3f5-3195-4cd2-be19-53b4c0a11155', '申请单', 'apply/list', '3a50d43b-4e11-4036-a378-464a024353ce', 4, null,
        'mdi-truck-outline', 3, '15304', 0, null, null, null, 'select  *,
 OwerAtt=case when exists( select top 1 pm05801 from sdpm058
 where pm05801=pm03401) then 1 else 0 end
 from sdvw_sdpm034 Where 1=1 '),
       ('2c2ed96f-e89b-4922-899b-2f6aea1a1547', '部门管理', 'personnel/section', 'ab73c942-0ba4-46b4-aeda-4a4cc00408e8',
        11, null, 'mdi-graph', 3, null, 0, null, null, null, null),
       ('2ef167bb-baa0-4c1c-bb33-bdb24a5918dd', '参数设置', 'system/config/list',
        '3e1e0753-3960-4f24-a53f-3b9a2cbca98d', 8, null, 'mdi-cog', 3, null, 0, null, null, null, null),
       ('2f8d9c0c-984d-4e12-8b23-185ec8033e8a', '已办流程', 'flow/approveList', '0bfb09fd-766b-4131-bb2f-00d64cad619d',
        9, null, '', null, null, null, null, null, null, null),
       ('304002ec-7679-4e2d-b80b-ba26b20a9b8c', '对账单2', 'finance/pro_detail_colie',
        '9d45c707-f052-4ec4-92af-db2476c9b73d', 3, null, '', null, null, null, null, null, null, null),
       ('316aa0a1-063c-4bcd-87af-6284beb66665', '材料导入', '/managent/importMater',
        'b4ba2c8f-df8b-4fa2-baa4-2229a66d9342', 1, null, '', null, null, null, null, null, null, null),
       ('330b2822-bba2-47e9-9f81-373ee5d1745b', '材料计划单', 'plan/plan', '3a50d43b-4e11-4036-a378-464a024353ce', 2,
        '', 'mdi-floor-plan ', 3, '13270', 0, '/pm2-vue/#/plan/plan', null, null, 'select * from(
select sdpm071.*,pa00102,
pa00303=ISNULL((Select top 1 pa00303 from sdpa003 where pa00302=pm07103),''''),
PerName=isnull((select pj00402 from sdpj004 where pj00421=pm07107),'''')
,OAState=isnull((select po00308 from sdpo003 where po00307=pm07101),0)
,pm07110Name=pj00402
from sdpm071
left join sdpa001 on pa00101=pm07102
left join sdpj004 on pj00421=pm07110
)#temp Where 1=1 '),
       ('35c0d71d-f387-4056-b96d-1609fc68a0e1', '定时任务管理', 'system/job/list',
        '3e1e0753-3960-4f24-a53f-3b9a2cbca98d', 9, null, 'mdi-timer', 3, null, 0, null, 0, null, null),
       ('3a49057f-4607-47c0-9a71-db24f0420cee', '公司架构', 'personnel/companyTree',
        '8d1631e5-e9c8-43c8-a6c5-59a541860c43', 5, null, '', null, null, null, null, null, null, null),
       ('3a50d43b-4e11-4036-a378-464a024353ce', '项目管理', 'project/index', '', 3, '', 'mdi-city', 3, '', 0, null,
        null, null, null),
       ('3adff6bd-6906-4dca-afbd-a93ec9d33997', '材料库导入', '/managent/getMaterByProId',
        'b4ba2c8f-df8b-4fa2-baa4-2229a66d9342', 2, null, '', null, null, null, null, null, null, null),
       ('3dc87dfa-a1cc-4be9-bc69-c03ef7126fdd', '材料历史价格查询', 'project/materHistory',
        '3a50d43b-4e11-4036-a378-464a024353ce', 11, null, 'mdi-history ', 3, null, 0,
        '/managent/getPage?pageName=project/material_history_price', null, null, null),
       ('3e1e0753-3960-4f24-a53f-3b9a2cbca98d', '系统配置', '', '', 9, '', 'mdi-cog', 3, '', 0, null, null, null, null),
       ('4196e3eb-95fa-4525-b1f6-765f1d6dd260', '报销统计', 'expense/totalExpense',
        'e3a1d279-9b5f-44ef-b8dc-95a2035c3438', 9, null, 'mdi-finance', 3, '', 0, null, null, null, null),
       ('4386c324-7fd7-420f-9cfd-4296b67729e6', '报价记录', 'project/quote_list',
        '8731ebf3-ec22-4978-b64c-bc0b5d6fe4d9', 4, null, '', null, null, null, null, null, null, null),
       ('43b53d50-ceab-461d-9217-4aedcb12eb69', '相关单位管理', 'company/list', '3e1e0753-3960-4f24-a53f-3b9a2cbca98d',
        3, null, 'mdi-domain', 3, null, 0, '/managent/getPage?pageName=company/companyList', null, null, null),
       ('43e552b9-7191-423d-b417-9f8204a01027', '收票管理', 'bill/proBill', '1e8c7a8e-bf99-47d3-974d-0d3cf339c985', 0,
        '', '', 3, '', null, null, null, null, null),
       ('445b2808-f102-4f68-8e0e-9449bdb07a7b', '办公用品导入', 'workMaterial/put',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 98, null, 'mdi-import', 3, null, 0, null, null, null, null),
       ('45047db7-b640-49a2-a05d-64446b199b41', '加班报表', 'personnel/overtimes',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 4, null, 'mdi-calendar-arrow-left', 3, null, 0,
        '/managent/getPage?pageName=personnel/overtime', null, null, null),
       ('453dc27a-bd29-4eee-9c95-f3e3f875fbd2', '报销科目统计', '/managent/getDcTypeMax',
        '0bfb09fd-766b-4131-bb2f-00d64cad619d', 64, null, '', null, null, null, null, null, null, null),
       ('484a0f85-4f00-4b2c-9fc3-b5d5fab96968', '考勤管理', 'personnel/WorkCheck',
        '8d1631e5-e9c8-43c8-a6c5-59a541860c43', 7, null, '', null, null, null, null, null, null, null),
       ('4bf4d78d-364a-433d-8217-934fcd6c651f', '用车申请单', 'applyForCar/applyForCar',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 9, null, 'mdi-car', 3, null, 0, null, null, null, null),
       ('4c2711af-5138-4d0e-892b-d14b7ce8d0d2', '请假单报表', 'personnel/leaves',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 3, null, 'mdi-calendar-arrow-right', 3, null, 0,
        '/managent/getPage?pageName=personnel/leaves', null, null, null),
       ('4c8a3b8e-fe78-479a-bc3c-f48987679d68', '采购合同', 'purchaseContractForKaiLi/list/contractList',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 6, '', 'mdi-file-edit-outline', 3, '10564', 0,
        '/managent/getPage?pageName=contract/c_msg_list', null, null, 'SELECT sdvw_sdpd004.*,
PayAmt=isnull((select sum(pd01725) from sdpd017 where pd01701=pd00401 ),0),
PayCount=isnull((select count(1) from sdpb020,sdpd017
where pb02011=pd01702 and pb02022=1 and pd00401=pd01701),0),
CurrencyMark=( select isnull(pj00105,'''') from sdpj001 where pj00101=pd00417 ),
/*发起OA流程的审批状态*/
OAState=isnull((select po00308 from sdpo003 where po00307=pd00401),0),
 OwerAtt=case when exists( select top 1 pd01301 from sdpd013
 where pd01301=pd00401) then 1 else 0 end
FROM sdvw_sdpd004 Where 1=1 '),
       ('4defc7b5-473d-4aa2-a4e2-77eb5195fcf5', '材料到货情况', 'procurement/noDhList/:projectId?',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 14, null, 'mdi-car', 3, null, 0, null, null, null, null),
       ('506447a3-f278-4492-bfc2-5899e1fdd312', '已办流程', 'flow/flowCount', 'c436da27-6b7a-44c8-88d0-ce572b89ab8a', 1,
        null, 'mdi-stack-overflow', 3, null, 0, '/managent/getPage?pageName=flow/approveList', null, null, null),
       ('5067a1ea-ab14-4fa6-a517-34582c3923d5', '项目出库记录', 'outmaterial/report',
        'e3a1d279-9b5f-44ef-b8dc-95a2035c3438', 2, null, 'mdi-finance', 3, null, 0, null, 0, null, null),
       ('50a70bf2-c0b9-4133-b6fe-19a8c2a2b9ed', '年度采购材料分析', '/managent/getMaterialByProMax',
        '0bfb09fd-766b-4131-bb2f-00d64cad619d', 65, null, '', null, null, null, null, null, null, null),
       ('51c68811-f43a-41b9-9961-cb391019ef8d', '成本科目', 'expense/course', '3a50d43b-4e11-4036-a378-464a024353ce', 6,
        null, 'mdi-golf', 3, null, 0, '/managent/getPage?pageName=project/course', null, null, null),
       ('51d71956-a1a7-47b3-8795-ad372944582e', '我的加班', 'workOvertimeForm/workOvertimeForm',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 2, null, 'mdi-calendar-arrow-left', 3, '1320276', 0, null, null, null, 'select * from(
select id,staff,date,overtime,begin_time,end_time
,remark,hour,name,
isnull(sdpj003.pj00302,'''''''') as Department,
isnull(sdpj004.pj00402,'''''''') as Personnel,
OAState=isnull((select po00308 from sdpo003 where po00307=id),0)

from pro_overtime
left join sdpj004 on staff=pj00401
left join sdpj003 on pj00417=pj00301
)#Temp Where 1=1'),
       ('541b1fb4-1727-44eb-b4de-3569e95fe986', '员工充值记录', 'account/history',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 10, null, 'mdi-history', 3, null, 0, null, null, null, null),
       ('541fc5d9-c6de-4f2f-a1bc-7e9db95dd573', '入库单管理', 'storage/put/list',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 7, null, 'mdi-warehouse ', 3, null, 0,
        '/managent/getPage?pageName=putStorage/putList', null, null, null),
       ('54eb15c4-b8bf-4893-b2e2-f6f6f49eb8ad', '发货申请单', 'project/proSend', '3a50d43b-4e11-4036-a378-464a024353ce',
        10, null, 'mdi-file-send ', 3, null, 0, '/managent/getPage?pageName=project/proSend', null, null, null),
       ('5605136f-c952-4380-b893-2a0cc6595b35', '立体停车对账单', 'finance/pro_detail_lt',
        '9d45c707-f052-4ec4-92af-db2476c9b73d', 2, null, '', null, null, null, null, null, null, null),
       ('58639ca1-a62a-486c-bb9a-863cb6cdabc1', '采购明细对账单', 'finance/pro_detail_list',
        '9d45c707-f052-4ec4-92af-db2476c9b73d', 1, null, '', null, null, null, null, null, null, null),
       ('59cc7076-d01d-4e62-8e4e-ef880963a202', '采购明细对账单', 'finance/procurementList',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 1, null, 'mdi-account-details-outline', 3, null, 0,
        '/managent/getPage?pageName=finance/pro_detail_list', null, null, null),
       ('5a0c61f1-ead9-4f79-94f4-f43d59c6c341', '退款记录', 'account/diningRefund',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 14, null, 'mdi-history', 3, null, 0, null, null, null, null),
       ('5b6e03ba-f8e0-4d1b-b905-92c1e208494e', '分包合同盖章申请', 'proSubcontract/list/list',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 5, null, 'mdi-book', 3, null, 0, null, null, null, null),
       ('5c06fc24-0642-4efc-9bfc-cb3b55ab7b21', '我的报销', 'expense/personalExpense',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 5, null, 'mdi-receipt ', 3, '1320284', 0,
        '/managent/getPage?pageName=finance/my_declare', null, 'expense', null),
       ('5c3d3787-c8b9-4ed2-8326-a4a39951d400', '项目领料记录', 'outmaterial/report',
        '8c9d5705-6cf9-4153-9153-e8f73462656f', 6, '', 'mdi-clock-check', 3, null, 0, null, 0, null, null),
       ('5c9df44c-1283-4701-8713-2c4cfb598993', '设备送修', 'equipmentToRepair/list',
        '9e755b32-b3d2-4e95-ba42-81476e55b062', 0, null, 'mdi-cube-send', 3, null, 0, null, 0, null, null),
       ('5cf31ce6-518d-48df-bdf3-5fdfdb38f2c8', '我的办文', 'approve/approve', null, 0, null, '', null, null, null,
        null, null, null, null),
       ('622b5408-4e18-4314-a4f6-53e01c1efa18', '未发送订单', 'procurement/pmListWait',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 3, null, 'mdi-send-check', 3, null, 0,
        '/managent/getPage?pageName=procurement/pmListWait', null, null, null),
       ('63d7150e-1950-4ea3-bdb9-399b47840661', '供货商入库记录', 'company/companyMater',
        '8731ebf3-ec22-4978-b64c-bc0b5d6fe4d9', 5, null, '', null, null, null, null, null, null, null),
       ('65086254-c172-47f7-8080-5bff30dd20d5', '维修登记', 'projectAfter/insert',
        '9e755b32-b3d2-4e95-ba42-81476e55b062', 1, null, 'mdi-plus', 3, null, 0, null, null, null, null),
       ('6b8d1982-b502-47aa-9243-9aba0e628e68', '项目成本统计', 'project/proUseMsg',
        'e3a1d279-9b5f-44ef-b8dc-95a2035c3438', 1, null, 'mdi-finance', 3, null, 0, null, 0, null, null),
       ('6ec53c10-605a-4117-820e-e5ecd3f3349d', '考勤校准', 'personnel/workCheckApprove',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 6, null, 'mdi-clock-check', 3, null, 0,
        '/managent/getPage?pageName=personnel/WorkCheckApprove', null, null, null),
       ('6ed7ea4e-10bb-4ce5-af24-461e96bb50d1', '我的请假', 'vacation/vacation', 'a59d92e0-9f04-449a-b942-6acf6704f9f0',
        0, '', 'mdi-calendar-arrow-right', 3, '137214', 0, '/pm2-vue/#/vacation/vacation', null, null, 'SELECT * FROM(
select sdpo057.*,
po05703Name=ISNULL((select pj00402 from sdpj004 where pj00401=po05703),''''''''),
po05704Name=ISNULL((select pj00302 from sdpj003 where pj00301=po05704),''''''''),
po05710Name=ISNULL((select pj00402 from sdpj004 where pj00401=po05710),''''''''),
po05713Name=ISNULL((select pj00402 from sdpj004 where pj00401=po05713),''''''''),
pa00102=ISNULL((select pa00102 from sdpa001 where pa00101=po05716),''''''''),
OAState=isnull((select po00308 from sdpo003 where po00307=po05701),0)
from sdpo057
)#TEMP Where 1=1'),
       ('6f7d15de-5d1e-43a3-bf8d-9bded47a1055', '工程合同', 'salesContract/list/salesContractList',
        'e7afc22c-d4fd-4846-ace0-c47bd086fe98', 1, null, 'mdi-book', 3, null, 0, null, null, 'SalesContract', null),
       ('709eec78-a94b-4fa3-8f02-098c74f0f72e', '用车统计', 'wxLocation/list', 'ab73c942-0ba4-46b4-aeda-4a4cc00408e8',
        8, null, 'mdi-clock-check', 3, null, 0, null, 0, null, null),
       ('70f26ecf-6ca2-427c-b2da-c2d249566e49', '供应商入库记录', 'company/putHistory/:companyId?/:companyName?',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 10, null, 'mdi-history', 3, null, 0,
        '/managent/getPage?pageName=company/companyMater', null, null, null),
       ('73a88d8f-c4ae-47c2-8ae1-c172c0513e90', '项目耗材最多报表', '/managent/getProjectByOutNumber',
        '0bfb09fd-766b-4131-bb2f-00d64cad619d', 62, null, '', null, null, null, null, null, null, null),
       ('74ede8f0-69b8-4f2b-95c3-ff03355e0e35', '公司架构', 'personnel/dutys', 'ab73c942-0ba4-46b4-aeda-4a4cc00408e8',
        1, null, 'mdi-graph', 3, null, 0, '/managent/getPage?pageName=personnel/companyTree', null, null, null),
       ('763af978-de49-4e02-a6b6-aacf807a4de0', '购买数量最多单位分析', '/managent/getProMoneyByCompanyMax',
        '0bfb09fd-766b-4131-bb2f-00d64cad619d', 67, null, '', null, null, null, null, null, null, null),
       ('781d4ef7-a039-430d-95a7-9b0f0f28ea42', '就餐记录', 'account/diningHistory',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 11, null, 'mdi-history', 3, null, 0, null, null, null, null),
       ('78473112-4c9e-46c1-9acb-10635b87a255', '权限管理', 'personnel/MenuManager',
        '8d1631e5-e9c8-43c8-a6c5-59a541860c43', 1, null, '', null, null, null, null, null, null, null),
       ('7b03f94e-5b71-4e5d-8e1c-105bf56c4aa5', '考勤报表', 'workcheck/deptList',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 5, null, 'mdi-clock-check', 3, null, 0,
        '/managent/getPage?pageName=personnel/WorkCheck', null, null, null),
       ('7ecbc999-5ead-4b4b-a44a-ebdbd5e53ead', '出库管理', 'storage/out/list', '8c9d5705-6cf9-4153-9153-e8f73462656f',
        2, null, 'mdi-home-export-outline', 3, null, 0, null, 0, null, null),
       ('7fe28594-e8b4-4a1f-84a1-c7aba1a2f2f9', '采购/入库/付款月报表', '/managent/getProByMonth',
        '0bfb09fd-766b-4131-bb2f-00d64cad619d', 68, null, '', null, null, null, null, null, null, null),
       ('80f3692d-f8d4-4dfb-a6db-eb5c4350730d', '角色管理', 'personnel/roles', 'ab73c942-0ba4-46b4-aeda-4a4cc00408e8',
        10, null, 'mdi-account-details-outline', 3, null, 0, null, null, null, null),
       ('837a6dc6-22eb-48b4-b596-c1c63b4db2db', '固定资产采购申请', 'fixedAssets/index',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 14, null, 'mdi-book-edit', 3, null, 0, null, 0, null, null),
       ('840de61b-6ac4-49b3-898b-b28c2385d204', '申请单', 'procurement/apply/list',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 1, '', 'mdi-arrange-bring-forward', 3, '', 0,
        '/managent/getPage?pageName=apply/applyList', null, null, null),
       ('84229438-ada9-415a-ac29-9df1d5912e8d', '库存管理', 'storage/inventory/list',
        '8c9d5705-6cf9-4153-9153-e8f73462656f', 3, null, 'mdi-home', 3, null, 0, null, 0, null, null),
       ('844f3684-44a3-4723-a01c-79151d49d9c6', '材料采购历史价格查询', 'project/material_history_price',
        '8731ebf3-ec22-4978-b64c-bc0b5d6fe4d9', 6, null, '', null, null, null, null, null, null, null),
       ('8501f9c4-b43b-4885-8775-e42c5f72e6f8', '职员管理', 'personnel/users', 'ab73c942-0ba4-46b4-aeda-4a4cc00408e8',
        2, null, 'mdi-account-group', 3, null, 0, '/managent/getPage?pageName=personnel/nel_manager', null, null, null),
       ('8731ebf3-ec22-4978-b64c-bc0b5d6fe4d9', '项目管理', 'project/plan', '', 2, null, '', null, null, null, null,
        null, null, null),
       ('875e6ea3-802a-4a50-994b-e2bc974e67cd', '采购金额统计', 'procurement/moneyReport',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 11, null, 'mdi-finance', 3, null, 0, null, null, null, null),
       ('889eac27-6925-4441-ba39-769d2b517a3e', '公司公告新增', '/managent/insertInfo',
        '8d1631e5-e9c8-43c8-a6c5-59a541860c43', 206, null, '', null, null, null, null, null, null, null),
       ('89d14e2e-1c7f-4bc2-86c2-f1369ca6b7ec', '存量运营资产', 'finance/house_bill',
        '9d45c707-f052-4ec4-92af-db2476c9b73d', 2, null, '', null, null, null, null, null, null, null),
       ('8c9d5705-6cf9-4153-9153-e8f73462656f', '仓储管理', 'storage/put/list', null, 8, null, 'mdi-warehouse', 3, null,
        0, null, 0, null, null),
       ('8d1631e5-e9c8-43c8-a6c5-59a541860c43', '行政人事管理', '''''', '', 1, null, '', null, null, null, null, null,
        null, null),
       ('8e654281-4bc2-4622-b2fb-0e0ddf41b03a', '年度报表', 'salesAndPurchaseDataReport/salesAndPurchaseYearDataReport',
        'e7afc22c-d4fd-4846-ace0-c47bd086fe98', 99, null, 'mdi-calendar', 3, null, 0, null, null, null, null),
       ('8eb9d663-5b20-45d3-a9df-6a484c704aee', '项目材料统计', 'project/proUseMsg',
        '8731ebf3-ec22-4978-b64c-bc0b5d6fe4d9', 3, null, '', null, null, null, null, null, null, null),
       ('8fdfc92a-fcb6-4638-8655-f63a2c2eb52e', '入库签字列表', 'storage/put/signList/:all?',
        '3a50d43b-4e11-4036-a378-464a024353ce', 11, null, 'mdi-home', 3, null, 0, null, 0, null, null),
       ('90e686eb-73bf-46f1-8e2c-3211d4a70557', '项目管理', 'project/index', '3a50d43b-4e11-4036-a378-464a024353ce', 1,
        null, 'mdi-city ', 3, null, 0, '/managent/getPage?pageName=project/projectList', null, null, null),
       ('9126861b-36c1-45e8-b5df-c3cea25b1f0c', '材料计划', 'plan/plan', '9126861b-36c1-45e8-b5df-c3cea25b1f0c', 0, '',
        '', 3, '', null, null, null, null, null),
       ('92a10ef5-a0a6-40f1-a284-c4c52c662044', '项目退库记录', 'storage/back/report',
        'e3a1d279-9b5f-44ef-b8dc-95a2035c3438', 3, null, 'mdi-finance', 3, null, 0, null, 0, null, null),
       ('93eaa4bb-6e7d-43ae-8319-2c305f9d0023', '审批|知会消息', 'approve/approve',
        '5cf31ce6-518d-48df-bdf3-5fdfdb38f2c8', 1, null, '', null, null, null, null, null, null, null),
       ('94de8329-4a0a-426d-a6c4-7bf57b2536e2', '库存管理', 'storage/inventory/list',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 8, null, 'mdi-home', 3, null, 0,
        '/managent/getPage?pageName=material/MaterialList', null, null, null),
       ('960a2655-9ec8-41e4-9cd7-414867656094', '出差统计', 'travelApplication/travelApplicationAll',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 6, null, 'mdi-clock-check', 3, null, 0, null, null, null, null),
       ('961aca91-8890-4b76-8989-bd87463e84ef', '我的审批', 'approve/index', 'a59d92e0-9f04-449a-b942-6acf6704f9f0', -1,
        null, 'mdi-book', 3, null, 0, null, 0, null, null),
       ('9d45c707-f052-4ec4-92af-db2476c9b73d', '财务管理', 'finance/pro_detail_list', '', 3, null, '', null, null,
        null, null, null, null, null),
       ('9dfefa76-0e7c-4ed9-b30b-1b9c6aa4bb0f', '材料计划', 'plan/plan', '1', 0, '', '', 3, '', null, null, null, null,
        null),
       ('9e755b32-b3d2-4e95-ba42-81476e55b062', '售后服务', 'projectAfter/list', null, 7, null,
        'mdi-table-column-plus-after', 3, null, 0, null, null, null, null),
       ('a18e9f15-b03c-4537-af3c-775c801011ef', '立体停车对账单', 'finance/proDetailList-lt',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 2, null, 'mdi-account-details-outline', 3, null, 0, '', null, null,
        null),
       ('a3f144cb-1479-42da-8ff0-61b2f094f929', '招待申请', 'entertain/list/:add?',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 10, null, 'mdi-glass-wine', 3, '1320286', 0, null, 0, null, 'select * from(
                 select id,staff,pj00302 as Department,
                        entertain_dining_standard,
                        entertain_types,
                        pj00402 as Personnel,
                     entertain_carOAState=isnull((select po00308 from sdpo003 where po00307=id),0)

                 from entertain left join sdpj003 on section=pj00301 left join sdpj004 on staff=pj00401
             )#Temp Where 1=1'),
       ('a59d92e0-9f04-449a-b942-6acf6704f9f0', '我的办文', 'approve/index', '', 2, '', 'mdi-briefcase-edit-outline', 3,
        '', 0, null, null, null, null),
       ('ab73c942-0ba4-46b4-aeda-4a4cc00408e8', '综合管理部', 'personnel/infoList', null, 8, null,
        'mdi-account-network', 3, null, 0, '/managent/getPage?pageName=personnel/companyTree', null, null, null),
       ('aee9e05d-c48e-4d2f-b64e-ebd8ec5ce216', '工作台', 'work/panel', '06058a9d-445d-495d-bafa-ef1f739d1469', 12,
        '采购部工作台', 'mdi-sitemap', 3, null, 0, null, null, null, null),
       ('af56883b-271d-4526-bea2-4953705f2ea7', '就餐申请', 'applyDine/list', 'a59d92e0-9f04-449a-b942-6acf6704f9f0',
        11, null, 'mdi-glass-wine', 3, '1320280', 0, null, 1, null, 'select * from(
select id,
isnull(sdpj003.pj00302,'''''''') as Department,
isnull(sdpj004.pj00402,'''''''') as Personnel,
OAState=isnull((select po00308 from sdpo003 where po00307=id),0)

from pro_apply_dine
left join sdpj003 on section_id=pj00301
left join sdpj004 on staff_id=pj00401
)#Temp Where 1=1'),
       ('b06a4c51-ae1b-45ca-8485-72e0b107a187', '退库管理', 'storage/back/list', '8c9d5705-6cf9-4153-9153-e8f73462656f',
        7, null, 'mdi-keyboard-return', 3, null, 0, null, 0, null, null),
       ('b1df7cae-5ae6-463d-862b-655f680a998a', '系统日志', 'system/log', '3e1e0753-3960-4f24-a53f-3b9a2cbca98d', 7,
        null, 'mdi-math-log', 3, null, 0, null, null, null, null),
       ('b23b801f-cdd2-43cc-873e-16f6d28bee5f', '工程分布', 'personnel/workMap', '3a50d43b-4e11-4036-a378-464a024353ce',
        12, null, 'mdi-google-maps', 3, null, 0, '/managent/getPage?pageName=personnel/WorkMap', null, null, null),
       ('b3578a53-5cdd-4031-b79c-69913cb90829', '招标信息提醒', 'reptile/list', 'e7afc22c-d4fd-4846-ace0-c47bd086fe98',
        8, null, 'mdi-search-web', 3, null, 0, null, 0, '', null),
       ('b3da34a3-d530-4d90-b0d1-bf4587c27be6', '订单管理', 'procurement/list/list',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 2, null, 'mdi-cart-check', 3, null, 0,
        '/managent/getPage?pageName=procurement/pmList', null, null, null),
       ('b4ba2c8f-df8b-4fa2-baa4-2229a66d9342', '申请单', 'apply/applyList', '8731ebf3-ec22-4978-b64c-bc0b5d6fe4d9', 2,
        null, '', null, null, null, null, null, null, null),
       ('b515f4ef-a77d-4f3d-813c-3fa52b1d03b8', '对账单2', 'finance/proDetailList-colie',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 3, null, 'mdi-account-details-outline', 3, null, 0, null, null, null,
        null),
       ('b5d295bb-a25d-4973-9ecc-b9af906cbfd5', '办公用品领用统计', 'workMaterial/history',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 100, null, 'mdi-finance', 3, null, 0, null, null, null, null),
       ('ba329658-70b5-4059-b140-bf68d95f21e8', '加班报表', 'personnel/overtime',
        '8d1631e5-e9c8-43c8-a6c5-59a541860c43', 3, null, '', null, null, null, null, null, null, null),
       ('baaf7e85-aa1f-47db-8648-109494909e37', '员工就餐页面', 'account/dining',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 99, null, 'mdi-dining', 3, null, 0, null, null, null, null),
       ('bdc232f3-c81c-44bb-911d-72feeddb4d33', '待签字列表', 'storage/put/signListAll',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 15, null, 'mdi-pencil-outline', 3, null, 0, null, 0, null, null),
       ('c1437287-65e3-4d40-ae7a-600530dd10ca', '询价记录', 'procurement/query/list',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 14, null, 'mdi-clock-outline', 3, null, 0, null, 0, null, null),
       ('c1a6a838-2da1-41e2-ab69-9cd4526faeaf', '项目报销费用统计', '/managent/getMoneyByProject',
        '0bfb09fd-766b-4131-bb2f-00d64cad619d', 63, null, '', null, null, null, null, null, null, null),
       ('c3b9343c-527c-42f1-8c6e-6e82488fee67', '菜单管理', 'menu/index', '3e1e0753-3960-4f24-a53f-3b9a2cbca98d', 4, '',
        'mdi-format-list-numbered-rtl', 3, '', 0, null, null, null, null),
       ('c436da27-6b7a-44c8-88d0-ce572b89ab8a', '流程统计', 'flow/flowCount', null, 5, null, 'mdi-stack-overflow', 3,
        null, 0, null, null, null, null),
       ('c5b6be06-4bde-4782-b7da-f01adbc3da89', '员工饭卡账户', 'account/list', 'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5',
        9, null, 'mdi-account', 3, null, 0, null, null, null, null),
       ('c66be7ed-a83b-463d-838e-7022edc077b0', '工程付款统计', 'proSubcontractPay/list/count',
        'e3a1d279-9b5f-44ef-b8dc-95a2035c3438', 3, null, 'mdi-finance', 3, null, 0, null, 0, null, null),
       ('c7e6b32c-9c7c-4c01-a267-a09d48e58e41', '系统消息推送', 'personnel/notice',
        '3e1e0753-3960-4f24-a53f-3b9a2cbca98d', 5, null, 'mdi-bugle', 3, null, 0, null, null, null, null),
       ('c92a6454-09d1-4db3-a1c5-ffd30adb42e5', '入库管理', 'storage/put/list/:searchPutSerial?',
        '8c9d5705-6cf9-4153-9153-e8f73462656f', 1, null, 'mdi-home-import-outline', 3, null, 0, null, 0, null, null),
       ('ca3cab85-572b-4d70-959b-43f87621bda5', '存量经营资产', 'finance/house_bill',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 5, null, 'mdi-home-city', 3, null, 0,
        '/managent/getPage?pageName=finance/house_bill', null, null, null),
       ('caa049fa-e738-4e8d-a5c3-ee53322f9074', '开票记录', 'salesInvoiceReport/salesInvoiceReport',
        'e7afc22c-d4fd-4846-ace0-c47bd086fe98', 3, null, 'mdi-calendar-text', 3, null, 0, null, null, null, null),
       ('cebdda30-9a66-4f49-8938-d072e68643b3', '报销车费申请', 'wxLocation/myList',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 15, null, 'mdi-book', 3, '1320285', 0, null, 0, 'outCarExpense', 'select * from(
select id,staff_id,staff_name,section_id,section_name as Department
OAState=isnull((select po00308 from sdpo003 where po00307=id),0)

from out_car_history
)#Temp Where 1=1'),
       ('cfeb7ceb-5e39-4836-ad6c-542fde7dd204', '销售合同对账单', 'zj/sales/report',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 4, null, 'mdi-finance', 3, null, 0, null, null, null, null),
       ('d2dfff70-cac7-49e7-94e1-ee7d1f258656', '项目材料分析', 'project/proUseMsg',
        '3a50d43b-4e11-4036-a378-464a024353ce', 5, null, 'mdi-history', 3, null, 0,
        '/managent/getPage?pageName=project/proUseMsg', null, null, null),
       ('d52d78c1-26a1-4143-af8c-e4884c7505c0', '工程投标盖章申请', 'proBid/list',
        'e7afc22c-d4fd-4846-ace0-c47bd086fe98', 6, null, 'mdi-book-plus', 3, null, 0, null, null, null, null),
       ('d5477cca-0d69-4779-ac32-136810e6ba0d', '合同付款', 'contract/payment/list',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 6, null, 'mdi-contactless-payment-circle', 3, '10563', 0, '', null,
        null, 'Select * from sdvw_pd064 Where 1=1 '),
       ('d57631b9-5784-4f47-bb3f-8b3b2e5e9f12', '职员管理', 'personnel/nel_manager',
        '8d1631e5-e9c8-43c8-a6c5-59a541860c43', 4, null, '', null, null, null, null, null, null, null),
       ('d5cc5f7c-6697-46f4-9079-e59398866f97', '收票管理', 'finance/probill', '9d45c707-f052-4ec4-92af-db2476c9b73d',
        0, null, '', 3, '', null, null, null, null, null),
       ('d5cc5f7c-6697-46f4-9079-e59398866f98', '收票审核', '/api/proDetailDp/updateState',
        '9d45c707-f052-4ec4-92af-db2476c9b73d', 0, null, '', 3, '', null, null, null, null, null),
       ('d87e28ff-b263-4189-993f-4a951533dd4b', '作废订单', 'purchase/destroyPurchase',
        '06058a9d-445d-495d-bafa-ef1f739d1469', 5, '', 'mdi-cancel', 3, '', 0, '/pm2-vue/#/purchase/destorypurchase',
        null, null, null),
       ('d8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', '财务管理', 'wxLocation/myList', null, 9, null, 'mdi-finance', 3, null,
        0, null, null, null, null),
       ('d8c14574-c8dd-4621-9e87-7a7793dc2c7f', '开票记录', 'salesInvoiceReport/financeSalesInvoiceReport',
        'd8b93845-c4f2-4245-83ef-3bf8a7f1d2f5', 5, null, 'mdi-calendar-text', 3, null, 0, null, null, null, null),
       ('dbeb8fd3-edc9-419b-9309-4c55f09feb5d', '请假单报表', 'personnel/leaves',
        '8d1631e5-e9c8-43c8-a6c5-59a541860c43', 2, null, '', null, null, null, null, null, null, null),
       ('de78a9e5-6f60-4ee4-ad5c-2790767a5f67', '仓库管理', '', '', 5, null, '', null, null, null, null, null, null,
        null),
       ('e115ba48-834d-47a1-9c2c-4cc19805a444', '投标保证金', 'proBid/bzj', 'e7afc22c-d4fd-4846-ace0-c47bd086fe98', 7,
        null, 'mdi-book', 3, null, 0, null, null, null, null),
       ('e1c71e58-89b3-4cd7-9103-fc44789b9bfc', '证书管理', 'certificate/list', 'ab73c942-0ba4-46b4-aeda-4a4cc00408e8',
        11, null, 'mdi-file-certificate', 3, null, 0, null, null, null, null),
       ('e3a1d279-9b5f-44ef-b8dc-95a2035c3438', '成本分析', 'project/proUseMsg', null, 11, null, 'mdi-finance', 3, null,
        0, null, 0, null, null),
       ('e447d4d4-dec4-4d73-bafa-a7c55261d5ff', '入库单-审核', '/managent/approvePut',
        'de78a9e5-6f60-4ee4-ad5c-2790767a5f67', 11, null, '', null, null, null, null, null, null, null),
       ('e508216f-701e-44f9-ae78-16e1777c28b0', '办公用品领用', 'workMaterial/index',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 8, null, 'mdi-material-ui', 3, '15223', 0,
        '/managent/getPage?pageName=approve/workArticle', null, null, 'select * from (Select sdpo204.*,
pj00302=IsNull((Select pj00302 from sdpj003 where pj00301=po20404),''''),
pj00402=IsNull((Select pj00402 from sdpj004 where pj00401=po20405),'''')
from sdpo204    )temp Where 1=1 '),
       ('e706a959-fc69-4553-b439-955849611d63', '出差申请单', 'travelApplication/travelApplication.vue',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 4, null, 'mdi-airplane', 3, '1320274', 0, null, null, null, 'select * from(
select uuid,traveller,department,total_time,
OAState=isnull((select po00308 from sdpo003 where po00307=uuid),0)

from travel_application
)#Temp Where 1=1'),
       ('e7afc22c-d4fd-4846-ace0-c47bd086fe98', '销售管理', 'salesContract/list/salesContractList', null, 3, null,
        'mdi-book', 3, null, 0, null, null, null, null),
       ('e9baf86e-d6ef-431f-991b-7c4ad307d7ea', '项目责任人管理', 'projectAfter/personList',
        '9e755b32-b3d2-4e95-ba42-81476e55b062', 5, null, 'mdi-account-details-outline', 3, null, 0, null, 0, null,
        null),
       ('ebed8570-3d95-4760-8428-db72b6c8af72', '项目耗材最贵报表', '/managent/getProjectByOutMax',
        '0bfb09fd-766b-4131-bb2f-00d64cad619d', 62, null, '', null, null, null, null, null, null, null),
       ('f20204e8-76c2-490f-b262-7639e43ede28', '所有流程', 'flow/flowAll', 'c436da27-6b7a-44c8-88d0-ce572b89ab8a', 2,
        null, 'mdi-air-filter', 3, null, 0, null, null, null, null),
       ('f4ef5811-3711-4500-ad04-98a5d662c4e5', '公司证书打印', 'certificate/authorization',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 12, null, 'mdi-file-certificate-outline', 3, null, 0, null, null, null,
        null),
       ('f923433f-1320-4778-9a6d-9b95558a3d8b', '车费报销统计', 'wxLocation/expenseList',
        'e3a1d279-9b5f-44ef-b8dc-95a2035c3438', 3, null, 'mdi-car', 3, null, 0, null, 0, null, null),
       ('f9876cc5-beab-4903-97dc-103a072578c0', '月度报表',
        'salesAndPurchaseDataReport/salesAndPurchaseMonthDataReport', 'e7afc22c-d4fd-4846-ace0-c47bd086fe98', 98, null,
        'mdi-calendar', 3, null, 0, null, null, null, null),
       ('f9b411a6-f0ab-44fb-b468-4a3a55b91822', '流程管理', 'system/flowManager',
        '3e1e0753-3960-4f24-a53f-3b9a2cbca98d', 5, null, 'mdi-sitemap', 3, null, 0,
        '/managent/getPage?pageName=system/flow', null, null, null),
       ('fa96fe6f-c71b-4a02-9098-f5297a93d33a', '报修记录', 'projectAfter/list/:startDate?/:endDate?/:searchText?',
        '9e755b32-b3d2-4e95-ba42-81476e55b062', 2, null, 'mdi-history', 3, null, 0, null, null, null, null),
       ('fac21316-e4a1-4c1e-9368-d404d63396d7', '辅材管理', 'project/material', '3a50d43b-4e11-4036-a378-464a024353ce',
        2, null, 'mdi-assistant', 3, null, 0, null, null, null, null),
       ('fb116eb2-2524-4e26-983e-6880272a806e', '立体停车工程付款申请', 'article/lttc-pay',
        'a59d92e0-9f04-449a-b942-6acf6704f9f0', 7, null, 'mdi-contactless-payment-circle', 3, null, 0, null, null, null,
        null),
       ('fe52cfb1-b391-4cec-a9a9-fe2b67a24f83', '仓库盘点', 'storage/check/list',
        '8c9d5705-6cf9-4153-9153-e8f73462656f', 8, null, 'mdi-bank-check', 3, null, 0, null, 0, null, null),
       ('ffa09ea0-dbfa-40c6-8327-6b9824d1c153', '证书下载记录', 'certificate/downloadRecord',
        'ab73c942-0ba4-46b4-aeda-4a4cc00408e8', 99, null, 'mdi-history', 3, null, 0, null, 1, null, null);

INSERT INTO sdpo028 (po02801, po02802, po02803, po02804)
VALUES ('100111', '默认目录', '', '100111');

INSERT INTO sdpf001 (pf00101, pf00102, pf00103, pf00104, pf00105, pf00106)
VALUES ('10YA81JP', '供应商', '', '10YA81JP', '1', 2);
INSERT INTO sdpf001 (pf00101, pf00102, pf00103, pf00104, pf00105, pf00106)
VALUES ('D0&0P&ST', '甲方', '', 'D0&0P&ST', '6', 1);

INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('合同编号', N'#series#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('单位名称', N'#company#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('品牌', N'#brand#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('付款方式', N'#payType#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('租赁方式', N'#zlType#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('租赁面积', N'#acreage#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('租赁联系人', N'#zlPerson#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('租赁联系人手机号', N'#zlPersonTel#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ('业态对象', N'#yt#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'招商经办人', N'#staffName#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'递增金额', N'#dzNumber#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'合同应收金额（合同总价）', N'#ysMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'开票金额合计', N'#kpMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'招商实际已收合计', N'#sjMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'财务实际收款合计', N'#cwMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'备注', N'#remark#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'登记时间', N'#dateTime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'最后更新人姓名', N'#lastStaffName#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'最后更新时间', N'#lastDateTime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'商铺集合', N'#houses#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'年初欠款', N'#moneyOwe#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'年初欠票', N'#billOwe#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'当年租金', N'#yearRental#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'合同截止时间', N'#endDatetime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'合同开始时间', N'#startDatetime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'保证金', N'#bzjMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'保证金类型', N'#bzjType#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'已退保证金', N'#returnBzjMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'合同编号', N'#series#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'乙方单位', N'#brandCompany.name#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'品牌', N'#brand#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'付款方式', N'#payType#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'租赁方式', N'#zlType#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'租赁面积', N'#acreage#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'租赁联系人', N'#brandCompany.relationP#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'租赁联系人手机号', N'#brandCompany.telephoneP#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'业态对象', N'#yt#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'招商经办人', N'#staffName#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'计划进场日', N'#planDate#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'计划开业日', N'#openDate#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'递增金额', N'#dzNumber#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'合同应收金额（合同总价）', N'#ysMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'开票金额合计', N'#kpMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'招商实际已收合计', N'#sjMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'财务实际收款合计', N'#cwMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'备注', N'#remark#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'登记时间', N'#dateTime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'最后更新人姓名', N'#lastStaffName#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'最后更新时间', N'#lastDateTime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'商铺集合(铺位号)', N'#houses#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'年初欠款', N'#moneyOwe#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'年初欠票', N'#billOwe#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'当年租金', N'#yearRental#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'合同截止时间', N'#endDatetime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'合同开始时间', N'#startDatetime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'保证金', N'#bzjMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'保证金类型', N'#bzjType#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'已退保证金', N'#returnBzjMoney#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'商铺楼层', N'#houses.floor#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'租赁期限(月份)', N'#startDatetime.endDatetime#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'装修保证金(装修期装修押金)', N'#bzjList.装修保证金#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'筹备期装修管理费(元/m2)', N'#termList.筹备期装修管理费#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'营运期装修管理费(元/m2)', N'#termList.营运期装修管理费#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'装修建筑垃圾清运费(元/m2)', N'#termList.装修建筑垃圾清运费#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'临时水费(元/m2)', N'#termList.临时水费#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'临时电费(元/m2)', N'#termList.临时电费#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'履约保证金', N'#bzjList.履约保证金#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'质保保证金(质量保证金)', N'#bzjList.质保保证金#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'装修保证金.大写', N'#bzjList.装修保证金.大写#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'履约保证金.大写', N'#bzjList.履约保证金.大写#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'质保保证金.大写', N'#bzjList.质保保证金.大写#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'固定租金', N'#termList.固定租金#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'租金方式', N'#termList.租金方式#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'物业服务费(元/m2)(管理费)', N'#termList.管理费单价#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'物业服务费单价.大写(管理费)', N'#termList.管理费单价.大写#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'物业服务费(管理费)', N'#termList.管理费#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'物业服务费.大写(管理费)', N'#termList.管理费.大写#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'提成租金', N'#termList.提成租金#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'固定租金(优惠阶段)', N'#termList.固定租金(优惠阶段)#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'提成固定较高租金', N'#termList.提成固定较高租金#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'首期租金开始日期', N'#termList.首期租金开始日期#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'首期租金截止日期', N'#termList.首期租金截止日期#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'首期租金金额', N'#termList.首期租金金额#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'首期租金金额.大写', N'#termList.首期租金金额大写#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'首期物业服务费开始日期', N'#termList.首期物业服务费开始日期#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'首期物业服务费截止日期', N'#termList.首期物业服务费截止日期#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'首期物业服务费金额', N'#termList.首期物业服务费金额#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'首期物业服务费金额.大写', N'#termList.首期物业服务费金额大写#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'乙方', N'#partB.name#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'证件号码', N'#partB.idNumber#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'指定送达地址', N'#partB.address#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'指定联系电话', N'#partB.telephoneP#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'开户行', N'#partB.openAccount#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'账号', N'#partB.bankNumber#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'场地编号', N'#placeNum#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'场地面积', N'#placeArea#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'场地用途', N'#placeUseFor#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'开始日期', N'#startDate#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'截止日期', N'#endDate#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'场地费用标准(含税)', N'#price#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'场地费费总额(含税)', N'#money#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'场地费税率', N'#taxRate#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'交费结算期', N'#payCycle#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'水费单价', N'#waterPrice#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'保证金', N'#bond#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费标准4.4', N'#electricType.1#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费标准4.5', N'#electricType.2#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费标准4.6', N'#electricType.3#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费标准4.7', N'#electricType.4#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费金额4.4', N'#electricPrice.1#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费金额4.5', N'#electricPrice.2#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费金额4.6', N'#electricPrice.3#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费金额4.7', N'#electricPrice.4#', 2);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'乙方', N'#partB#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'场地编号', N'#placeNum#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'具体位置', N'#placeAddress#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'广告类别', N'#advertType#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'开始日期', N'#startDate#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'截止日期', N'#endDate#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'租金单价', N'#price#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'合计租金 大写', N'#capitalizationMoney#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'设计费', N'#designPrice#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'电费单价', N'#perElectricPrice#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'合计电费 大写', N'#capitalizationElectricMoney#', 3);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'推广次数', N'#tgfList.times#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'推广费用单价', N'#tgfList.price#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'推广费用', N'#tgfList.money#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'抵用券', N'#tgfList.voucher#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'违约金', N'#tgfList.liquidatedDamages#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'租赁方邮箱', N'#brandCompany.emailP#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'推广头条单价', N'#tgfList.firstBar#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'推广次条单价', N'#tgfList.secondBar#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'推广其他单价', N'#tgfList.otherBar#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES (N'甲方邮箱', N'#receivedCompany.emailP#', 0);
INSERT INTO contract_word_model_params ( name, mark_Name, type) VALUES ( N'广告位数量', N'#number#', 3);

