DROP TABLE IF EXISTS cl_decision;
create table cl_decision
(
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户标识',
  `borrow_id` bigint(20) NOT NULL COMMENT '订单id',
  `age` int(11) NOT NULL DEFAULT 0 COMMENT '年龄',
  `contact_num` int(11) NOT NULL DEFAULT 0 COMMENT '通讯录联系人数量',
  `contact_sensitive_num` int(11) DEFAULT 0 COMMENT '通讯录命中敏感性词汇(借、贷、催收)5个以上',
  `mx_is_name_and_idcard_in_court_black` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '身份证是否命中法院黑名单 0-未命中 1-命中',
  `mx_is_name_and_idcard_in_finance_black` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '身份证是否命中金融机构黑名单 0-未命中 1-命中',
  `mx_is_name_and_mobile_in_finance_black` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '手机号是否命中金融机构黑名单 0-未命中 1-命中',
  `mx_searched_org_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '查询过该用户相关企业数量',
  `mx_idcard_with_other_phone_num` int(11) NOT NULL DEFAULT 0 COMMENT '身份证号码组合过的其他号码数量',
  `mx_phone_no_voice_days` int(11) NOT NULL DEFAULT 0 COMMENT '180天内无通话记录天数',
  `mx_contact_loan_situation` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '与贷款类号码联系次数5次及以上，且主动呼叫占比大于50% 0-否 1-是',
  `mx_five_month_voice_situation` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '连续近5个月(当月不算在内)平均主叫次数20次以下并且连续近5个月(当月不算在内)平均通话时长(主叫+被叫)70分钟以下 0-否 1-是',
  `mx_five_month_bill_less_than_20_num` int(4) NOT NULL DEFAULT 5 COMMENT '连续近5个月(当月不算在内)话费消费低于20元的次数',
  `mx_contact_matching_voice_situation` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '通讯录与通话记录中匹配两个以下号码 0-否 1-是',
  `mx_is_reliability` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '手机号未实名认证(或认证的用户姓名与当前用户不匹配) 0-未实名认证 1-实名认证',
  `mx_province` varchar(24) NOT NULL DEFAULT '' COMMENT '户口所在省份',
  `mx_city` varchar(24) NOT NULL DEFAULT '' COMMENT '户口所在城市',
  `mx_region` varchar(24) NOT NULL DEFAULT '' COMMENT '户口所在县',
  `mx_native_place` varchar(24) NOT NULL DEFAULT '' COMMENT '籍贯',
  `mx_in_time` varchar(16) NOT NULL DEFAULT '' COMMENT '开户时长，单位月',
  `mx_bill_certification_day` varchar(32) NOT NULL DEFAULT '' COMMENT '账单认证日期',
  `mx_idcard_check` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证号码有效性',
  `mx_email_check` varchar(32) NOT NULL DEFAULT '' COMMENT '邮箱有效性',
  `mx_address_check` varchar(32) NOT NULL DEFAULT '' COMMENT '地址有效性',
  `mx_call_data_check` varchar(100) NOT NULL DEFAULT '' COMMENT '通话记录完整性',
  `mx_idcard_match` varchar(32) NOT NULL DEFAULT '' COMMENT '身份证号码是否与运营商数据匹配',
  `mx_name_match` varchar(32) NOT NULL DEFAULT '' COMMENT '姓名是否与运营商数据匹配',
  `mx_mobile_silence_3m` varchar(10) NOT NULL DEFAULT '' COMMENT '号码沉默度(近3月) 0-10分，分数越低越活跃',
  `mx_mobile_silence_6m` varchar(10) NOT NULL DEFAULT '' COMMENT '号码沉默度(近6月) 0-10分，分数越低越活跃',
  `mx_arrearage_risk_3m` varchar(10) NOT NULL DEFAULT '' COMMENT '欠费风险度(近3月) 0-10分，分值越高欠费风险越大',
  `mx_arrearage_risk_6m` varchar(10) NOT NULL DEFAULT '' COMMENT '欠费风险度(近6月) 0-10分，分值越高欠费风险越大',
  `mx_binding_risk` varchar(10) NOT NULL DEFAULT '' COMMENT '亲情网风险度 0-10分，分数越低，亲情网人数越大，加入时长越久',
  `mx_regular_circle` varchar(100) NOT NULL DEFAULT '' COMMENT '朋友圈在哪里',
  `mx_phone_used_time` varchar(100) NOT NULL DEFAULT '' COMMENT '号码使用时间',
  `mx_phone_power_off` varchar(100) NOT NULL DEFAULT '' COMMENT '关机情况',
  `mx_contact_each_other` varchar(100) NOT NULL DEFAULT '' COMMENT '互通过的电话号码数量',
  `mx_contact_macao` varchar(100) NOT NULL DEFAULT '' COMMENT '与澳门地区电话通话情况',
  `mx_contact_110` varchar(100) NOT NULL DEFAULT '' COMMENT '与110电话通话情况',
  `mx_contact_120` varchar(100) NOT NULL DEFAULT '' COMMENT '与120电话通话情况',
  `mx_contact_lawyer` varchar(100) NOT NULL DEFAULT '' COMMENT '与律师电话通话情况',
  `mx_contact_court` varchar(100) NOT NULL DEFAULT '' COMMENT '与法院电话通话情况',
  `mx_contact_loan` varchar(100) NOT NULL DEFAULT '' COMMENT '与贷款类号码联系情况',
  `mx_contact_bank` varchar(100) NOT NULL DEFAULT '' COMMENT '与银行类号码联系情况',
  `mx_contact_credit_card` varchar(100) NOT NULL DEFAULT '' COMMENT '与信用卡类号码联系情况',
  `mx_contact_collection` varchar(100) NOT NULL DEFAULT '' COMMENT '与催收类号码联系情况',
  `mx_contact_night` varchar(100) NOT NULL DEFAULT '' COMMENT '夜间活动情况',
  `mx_dwell_used_time` varchar(100) NOT NULL DEFAULT '' COMMENT '居住地本地(省份)地址在电商中使用时长 保留字段',
  `mx_ebusiness_info` varchar(100) NOT NULL DEFAULT '' COMMENT '总体电商使用情况 保留字段',
  `mx_person_ebusiness_info` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人本人电商使用情况 保留字段',
  `mx_virtual_buying` varchar(100) NOT NULL DEFAULT '' COMMENT '虚拟商品购买情况 保留字段',
  `mx_lottery_buying` varchar(100) NOT NULL DEFAULT '' COMMENT '彩票购买情况 保留字段',
  `mx_person_addr_changed` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人本人地址变化情况 保留字段',
  `mx_school_status` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人的学籍状态是否为在校学生 保留字段',
  `mx_education_info` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人的学历情况 保留字段',
  `mx_work_addr_info` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人本人最近使用工作地址情况 保留字段',
  `mx_live_addr_info` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人本人最近使用居住地址情况 保留字段',
  `mx_school_addr_info` varchar(100) NOT NULL DEFAULT '' COMMENT '申请人本人最近使用学校地址情况 保留字段',
  `mx_phone_call` varchar(100) NOT NULL DEFAULT '' COMMENT '号码通话情况',
  `mx_friend_num_3m` varchar(10) NOT NULL DEFAULT '' COMMENT '近3月朋友联系数量',
  `mx_good_friend_num_3m` varchar(10) NOT NULL DEFAULT '' COMMENT '近3月好朋友联系数量（联系10次以上）',
  `mx_friend_city_center_3m` varchar(50) NOT NULL DEFAULT '' COMMENT '近3月朋友圈中心城市',
  `mx_is_city_match_friend_city_center_3m` varchar(10) NOT NULL DEFAULT '' COMMENT '近3月朋友圈中心地是否与手机归属地一致',
  `mx_inter_peer_num_3m` varchar(10) NOT NULL DEFAULT '' COMMENT '近3月互通电话号码数目',
  `mx_friend_num_6m` varchar(10) NOT NULL DEFAULT '' COMMENT '近6月朋友联系数量',
  `mx_good_friend_num_6m` varchar(10) NOT NULL DEFAULT '' COMMENT '近6月好朋友联系数量（联系10次以上）',
  `mx_friend_city_center_6m` varchar(50) NOT NULL DEFAULT '' COMMENT '近6月朋友圈中心城市',
  `mx_is_city_match_friend_city_center_6m` varchar(10) NOT NULL DEFAULT '' COMMENT '近6月朋友圈中心地是否与手机归属地一致',
  `mx_inter_peer_num_6m` varchar(10) NOT NULL DEFAULT '' COMMENT '近6月互通电话号码数目',
  `mx_searched_org_type` varchar(255) NOT NULL DEFAULT '' COMMENT '查询过该用户的相关企业类型(姓名+身份证+电话号码)',
  `mx_idcard_with_other_names` text COMMENT '身份证组合过的其他姓名',
  `mx_idcard_with_other_phones` text COMMENT '身份证组合过其他电话',
  `mx_phone_with_other_names` text COMMENT '电话号码组合过其他姓名',
  `mx_phone_with_other_idcards` text COMMENT '电话号码组合过其他身份证',
  `mx_register_org_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '电话号码注册过的相关企业数量',
  `mx_register_org_type` varchar(255) NOT NULL DEFAULT '' COMMENT '电话号码注册过的相关企业类型',
  `mx_arised_open_web` text COMMENT '电话号码出现过的公开信息网站',
  `mx_phone_gray_score` decimal(5,2) NOT NULL DEFAULT 0.00 COMMENT '用户号码联系黑中介分数(0-100,分数越低风险越高，40分以下为高危人群)',
  `mx_contacts_class1_blacklist_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '直接联系人中黑名单人数',
  `mx_contacts_class2_blacklist_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '间接联系人中黑名单人数',
  `mx_contacts_class1_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '直接联系人人数',
  `mx_contacts_router_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '引起间接黑名单人数',
  `mx_contacts_router_ratio` decimal(5,2) NOT NULL DEFAULT 0.00 COMMENT '直接联系人中引起间接黑名单占比',
  `yx_overdue_history_count` int(11) NOT NULL DEFAULT 0 COMMENT '逾期历史数量',
  `yx_overdue_history_m3_count` int(11) NOT NULL DEFAULT 0 COMMENT '历史逾期M3+(大于90天)数量',
  `yx_overdue_history_m6_count` int(11) NOT NULL DEFAULT 0 COMMENT '历史逾期M6+(大于180天)数量',
  `yx_AM20_no_accept` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '申请借款数量大于20且放款数量为0,0-否 1-是',
  `mx_courtcase_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '法院执行人次数',
  `mx_dishonest_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '法院未执行次数',
  `mx_idcard_other_names_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '身份证存疑姓名数',
  `mx_idcard_other_mobiles_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '身份证存疑手机号码数',
  `mx_idcard_other_mobiles_black_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '身份证存疑触黑手机号码数',
  `mx_mobile_other_names_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '手机存疑姓名数',
  `mx_mobile_other_idcards_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '手机存疑身份证数',
  `mx_mobile_other_idcards_black_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '手机存疑触黑身份证数',
  `mx_other_devices_cnt` int(8) NOT NULL DEFAULT 0 COMMENT '使用过的设备数',
  `mx_mobile_other_devices_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '手机号码使用过的设备数',
  `mx_idcard_other_devices_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '身份证号码使用过的设备数',
  `mx_device_other_names_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '使用过的设备上登陆的其他姓名数',
  `mx_device_other_mobiles_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '使用过的设备上登陆的其他手机号码数',
  `mx_device_other_mobiles_black_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '使用过的设备上登陆的其他触黑手机号码数',
  `mx_device_other_idcards_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '使用过的设备上登陆的其他身份证号码数',
  `mx_device_other_idcards_black_cnt` int(11) NOT NULL DEFAULT 0 COMMENT '使用过的设备上登陆的其他触黑身份证号码数',
  `mx_black_mobile_name_in_blacklist` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '手机和姓名是否在黑名单 0-否 1-是',
  `mx_black_idcard_name_in_blacklist` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '身份证和姓名是否在黑名单 0-否 1-是',
  `mx_gray_mobile_name_in_blacklist` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '手机和姓名是否在灰名单 0-否 1-是',
  `mx_gray_idcard_name_in_blacklist` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '身份证和姓名是否在灰名单 0-否 1-是',
  `mx_fraud_is_hit` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否命中欺诈风险名单 0-否 1-是',
  `mx_fraud_hit_type` varchar(100) NOT NULL DEFAULT '' COMMENT '命中欺诈风险类型',
  `xd_is_lastloan_refused` varchar(8) NOT NULL DEFAULT 'false' COMMENT '最后一次申请是否被拒贷 true: 是；false: 否',
  `xd_total_loan_count` int(4) NOT NULL DEFAULT 0 COMMENT '历史借款次数(所有的借款次数，包含当前借款)',
  `xd_total_overdue_count` int(4) NOT NULL DEFAULT 0 COMMENT '历史逾期次数(所有的逾期次数，包含当前逾期)',
  `xd_longest_overdue_paid` varchar(16) NOT NULL DEFAULT '' COMMENT '已经还清的历史逾期最长时间，M1:小于1月; M2:大于1月，小于2月; M3:大于2月，小于3月; M4:3月及以上',
  `xd_current_overdue_count` int(4) NOT NULL DEFAULT 0 COMMENT '当前处于逾期状态的借款笔数',
  `xd_current_overdue_amount` int(4) NOT NULL DEFAULT 0 COMMENT '当前逾期总金额，0: 0(没有逾期); 1:[0,100]; 2:[100,500); 3:[500,1000); 4:[1000,2000); 5:[2000,4000); 6:[4000,6000); 7:[6000,10000); 8:>=10000',
  `xd_over_due90_contacts_count` int(4) NOT NULL DEFAULT 0 COMMENT '有逾期90天以上运营商联系人个数',
  `xd_longest_overdue_unpaid` varchar(16) NOT NULL DEFAULT '' COMMENT '当前最长逾期时间(不包括已经还清的)，M1:小于1月; M2:大于1月，小于2月; M3:大于2月，小于3月; M4:3月及以上',
  `xd_last_loan_refuse_reason` varchar(16) NOT NULL DEFAULT '' COMMENT '最后一次拒贷原因',
  `xd_last_loan_refuse_time` varchar(16) NOT NULL DEFAULT '' COMMENT '最后一次拒贷时间',
  `xd_remark` text COMMENT '其他详情',
  `xd_first_loan_time` varchar(32) NOT NULL DEFAULT '' COMMENT '最早借款时间',
  `ppx_is_black` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否不良用户 0-否 1-是',
  `ppx_is_alert` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否关注用户 0-否 1-是',
  `ppx_overdue_first_time` varchar(32) NOT NULL DEFAULT '' COMMENT '逾期最早出现时间',
  `ppx_overdue_last_time` varchar(32) NOT NULL DEFAULT '' COMMENT '逾期最近出现时间',
  `ppx_total_overdue_count` int(11) NOT NULL DEFAULT 0 COMMENT '逾期累计出现次数',
  `ppx_current_overdue_amount` int(4) NOT NULL DEFAULT 0 COMMENT '当前总逾期金额，0: 0(没有逾期); 1:[0,1000]; 2:[1000,2000); 3:[2000,3000); 4:[3000,4000); 5:[4000,6000); 6:[6000,8000); 7:[8000,10000); 8:[10000,30000); 9:[30000,50000); 10:[50000,100000); 11:>=10000',
  `ppx_current_overdue_days` int(4) NOT NULL DEFAULT 0 COMMENT '当前最大逾期时长 0:没有逾期 1:1-30天; 2:31-60天; M3:61-90天; 4:91-120天; 5:121-150天; 6:151-180天; 7:>180天',
  `ppx_history_overdue_amount` int(4) NOT NULL DEFAULT 0 COMMENT '历史最大逾期金额，0: 0(没有逾期); 1:[0,1000]; 2:[1000,2000); 3:[2000,3000); 4:[3000,4000); 5:[4000,6000); 6:[6000,8000); 7:[8000,10000); 8:[10000,30000); 9:[30000,50000); 10:[50000,100000); 11:>=10000',
  `ppx_history_overdue_days` int(4) NOT NULL DEFAULT 0 COMMENT '历史最大逾期时长 0:没有逾期 1:1-30天; 2:31-60天; M3:61-90天; 4:91-120天; 5:121-150天; 6:151-180天; 7:>180天',
  `ppx_fraud_first_time` varchar(32) NOT NULL DEFAULT '' COMMENT '欺诈最早出现时间',
  `ppx_fraud_last_time` varchar(32) NOT NULL DEFAULT '' COMMENT '欺诈最近出现时间',
  `ppx_total_fraud_count` int(11) NOT NULL DEFAULT 0 COMMENT '欺诈累计出现次数',
  `ppx_negative_first_time` varchar(32) NOT NULL DEFAULT '' COMMENT '负面最早出现时间',
  `ppx_negative_last_time` varchar(32) NOT NULL DEFAULT '' COMMENT '负面最近出现时间',
  `ppx_total_negative_count` int(11) NOT NULL DEFAULT 0 COMMENT '负面累计出现次数',
  `xy_loans_score` varchar(20) NOT NULL DEFAULT '' COMMENT '贷款行为分',
  `xy_loans_credibility` varchar(20) NOT NULL DEFAULT '' COMMENT '贷款行为置信度',
  `xy_loans_count` varchar(20) NOT NULL DEFAULT '' COMMENT '贷款放款总订单数',
  `xy_loans_settle_count` varchar(20) NOT NULL DEFAULT '' COMMENT '贷款已结清订单数',
  `xy_loans_overdue_count` varchar(20) NOT NULL DEFAULT '' COMMENT '贷款逾期订单数（M0+)',
  `xy_loans_org_count` varchar(20) NOT NULL DEFAULT '' COMMENT '贷款机构数',
  `xy_consfin_org_count` varchar(20) NOT NULL DEFAULT '' COMMENT '消费金融类机构数',
  `xy_loans_cash_count` varchar(20) NOT NULL DEFAULT '' COMMENT '网络贷款类机构数',
  `xy_latest_one_month` varchar(20) NOT NULL DEFAULT '' COMMENT '近1个月贷款笔数',
  `xy_latest_three_month` varchar(20) NOT NULL DEFAULT '' COMMENT '近3个月贷款笔数',
  `xy_latest_six_month` varchar(20) NOT NULL DEFAULT '' COMMENT '近6个月贷款笔数',
  `xy_history_suc_fee` varchar(20) NOT NULL DEFAULT '' COMMENT '历史贷款机构成功扣款笔数',
  `xy_history_fail_fee` varchar(20) NOT NULL DEFAULT '' COMMENT '历史贷款机构失败扣款笔数',
  `xy_latest_one_month_suc` varchar(20) NOT NULL DEFAULT '' COMMENT '近1个月贷款机构成功扣款笔数',
  `xy_latest_one_month_fail` varchar(20) NOT NULL DEFAULT '' COMMENT '近1个月贷款机构失败扣款笔数',
  `xy_loans_long_time` varchar(20) NOT NULL DEFAULT '' COMMENT '信用贷款时长',
  `xy_loans_latest_time` varchar(20) NOT NULL DEFAULT '' COMMENT '最近一次贷款时间',
  `pa_l1wwdcn_t_nums_con` int(11) NOT NULL DEFAULT 0 COMMENT '近一周与几个催收号码有过联系',
  `pa_l1wwdcn_t_nums_con_bank` int(11) NOT NULL DEFAULT 0 COMMENT '近一周与几家银行机构催收号码有过联系',
  `pa_l1wwdcn_t_nums_con_cf` int(11) NOT NULL DEFAULT 0 COMMENT '近一周与几家消费金融机构催收号码有过联系',
  `pa_l1wwdcn_t_nums_con_f` int(11) NOT NULL DEFAULT 0 COMMENT '近一周与几家委外催收机构催收号码有过联系',
  `pa_l1wwdcn_t_nums_con_if` int(11) NOT NULL DEFAULT 0 COMMENT '近一周与几家互联网金融机构催收号码有过联系',
  `pa_l1wwdcn_t_nums_con_org` int(11) NOT NULL DEFAULT 0 COMMENT '近一周涉及催收号码的总机构数',
  `pa_l1wwdcn_t_times_in` int(11) NOT NULL DEFAULT 0 COMMENT '近一周被催收号码呼叫次数',
  `pa_l1wwdcn_t_times_out` int(11) NOT NULL DEFAULT 0 COMMENT '近一周主叫催收号码次数',
  `pa_l2wwdcn_t_nums_con_org_type` int(11) NOT NULL DEFAULT 0 COMMENT '近两周联系机构类型总数',
  `pa_l3wwdcn_t_nums_con_if` int(11) NOT NULL DEFAULT 0 COMMENT '近三周联系互联网金融机构的总个数',
  `pa_l1mwdcn_t_nums_con` int(11) NOT NULL DEFAULT 0 COMMENT '近一月与几个催收号码有过联系',
  `pa_l1mwdcn_t_nums_con_bank` int(11) NOT NULL DEFAULT 0 COMMENT '近一月与几家银行机构催收号码有过联系',
  `pa_l1mwdcn_t_nums_con_cf` int(11) NOT NULL DEFAULT 0 COMMENT '近一月与几家消费金融机构催收号码有过联系',
  `pa_l1mwdcn_t_nums_con_f` int(11) NOT NULL DEFAULT 0 COMMENT '近一月与几家委外催收机构催收号码有过联系',
  `pa_l1mwdcn_t_nums_con_if` int(11) NOT NULL DEFAULT 0 COMMENT '近一月与几家互联网金融机构催收号码有过联系',
  `pa_l1mwdcn_t_nums_con_org` int(11) NOT NULL DEFAULT 0 COMMENT '近一月涉及催收号码的总机构数',
  `pa_l1mwdcn_t_times_in` int(11) NOT NULL DEFAULT 0 COMMENT '近一月被催收号码呼叫次数',
  `pa_l1mwdcn_t_times_out` int(11) NOT NULL DEFAULT 0 COMMENT '近一月主叫催收号码次数',
  `pa_l2mwdcn_t_times_in` int(11) NOT NULL DEFAULT 0 COMMENT '近两月被催收号码呼叫次数',
  `pa_l2mwdcn_t_nums_in` int(11) NOT NULL DEFAULT 0 COMMENT '近两月申请人收到催收号的总个数',
  `pa_l2mwdcn_tax_times_in` int(11) NOT NULL DEFAULT 0 COMMENT '近两月被单个催收号码呼叫的最大次数',
  `pa_l2mwdcn_max_times_con` int(11) NOT NULL DEFAULT 0 COMMENT '近两月申请人联系次数最大的催收号的总时长',
  `pa_l2mencn_t_tays_con` int(11) NOT NULL DEFAULT 0 COMMENT '近两月晚上联系催收号的总天数',
  `pa_l3mwdcn_t_nums_con` int(11) NOT NULL DEFAULT 0 COMMENT '近三月与几个催收号码有过联系',
  `pa_l3mwdcn_t_nums_con_bank` int(11) NOT NULL DEFAULT 0 COMMENT '近三月与几家银行机构催收号码有过联系',
  `pa_l3mwdcn_t_nums_con_cf` int(11) NOT NULL DEFAULT 0 COMMENT '近三月与几家消费金融机构催收号码有过联系',
  `pa_l3mwdcn_t_nums_con_f` int(11) NOT NULL DEFAULT 0 COMMENT '近三月与几家委外催收机构催收号码有过联系',
  `pa_l3mwdcn_t_nums_con_if` int(11) NOT NULL DEFAULT 0 COMMENT '近三月与几家互联网金融机构催收号码有过联系',
  `pa_l3mencn_t_nums_con_org` int(11) NOT NULL DEFAULT 0 COMMENT '近三月晚上涉及催收号码的总机构数',
  `pa_l3mwdcn_t_nums_con_org` int(11) NOT NULL DEFAULT 0 COMMENT '近三月涉及催收号码的总机构数',
  `pa_l3mwdcn_t_times_in` int(11) NOT NULL DEFAULT 0 COMMENT '近三月被催收号码呼叫次数',
  `pa_l3mwdcn_t_times_out` int(11) NOT NULL DEFAULT 0 COMMENT '近三月主叫催收号码次数',
  `pa_l3mwdcn_add_t_nums_in_org` int(11) NOT NULL DEFAULT 0 COMMENT '近三月新增机构数',
  `pa_l3mwdcn_max_days_in` int(11) NOT NULL DEFAULT 0 COMMENT '近三月被每个催收号呼叫的天数的最大值',
  `pa_l4mwdcn_t_nums_con_org_type` int(11) NOT NULL DEFAULT 0 COMMENT '近四月申请人联系机构类型的总个数',
  `pa_l4mwdcn_t_times_in_non_bank` int(11) NOT NULL DEFAULT 0 COMMENT '近四月非银机构呼入的总次数',
  `pa_l4mwdcn_t_dur_con` int(11) NOT NULL DEFAULT 0 COMMENT '近四月联系催收号的总时长',
  `pa_l4mwdcn_t_times_con` int(11) NOT NULL DEFAULT 0 COMMENT '近四月联系催收号的总次数',
  `pa_l4mwdcn_t_nums_con_f` int(11) NOT NULL DEFAULT 0 COMMENT '近四月与几家委外催收机构催收号码有过联系',
  `pa_l4mwdcn_t_dur_in` int(11) NOT NULL DEFAULT 0 COMMENT '近四月被催收号呼叫的总时长',
  `pa_l4mwdcn_t_dur_in_max_times` int(11) NOT NULL DEFAULT 0 COMMENT '近四月被叫次数最大的催收号的总时长',
  `pa_l4mwdcn_t_nums_in_f` int(11) NOT NULL DEFAULT 0 COMMENT '近四月被几家委外催收机构呼叫过',
  `pa_l5mwdcn_t_days_con` int(11) NOT NULL DEFAULT 0 COMMENT '近五月联系催收号的总天数',
  `pa_l5mwdcn_t_dur_in_f` int(11) NOT NULL DEFAULT 0 COMMENT '近五月被委外催收号码呼叫的总时长',
  `pa_l5mwdcm_t_dur_con` int(11) NOT NULL DEFAULT 0 COMMENT '近五月联系催收手机总时长',
  `pa_l5mwdcn_t_nums_con_if` int(11) NOT NULL DEFAULT 0 COMMENT '近五月与几家互联网金融机构催收号码有过联系',
  `pa_allwdcn_t_nums_con` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内与几个催收号码有过联系',
  `pa_allwdcn_t_nums_con_bank` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内与几家银行机构催收号码有过联系',
  `pa_allwdcn_t_nums_con_cf` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内与几家消费金融机构催收号码有过联系',
  `pa_allwdcn_t_nums_con_f` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内与几家委外催收机构催收号码有过联系',
  `pa_allwdcn_t_nums_con_if` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内与几家互联网金融机构催收号码有过联系',
  `pa_allwdcn_t_nums_con_org` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内涉及催收号码的总机构数',
  `pa_allwdcn_t_times_in` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内被催收号码呼叫次数',
  `pa_allwdcn_t_times_out` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内主叫催收号码次数',
  `pa_allwdcn_t_dur_in_f` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内被委外催收号码呼叫的总时长',
  `pa_allwdcm_max_org_type_in` int(11) NOT NULL DEFAULT 0 COMMENT '详单周期内被叫次数最大的催收手机号的机构类型',
  `pa_alldtcnwk_adur` int(11) NOT NULL DEFAULT 0 COMMENT '全部详单周期工作日(周一~周五)白天(8~18 点)_平 均时长',
  `pa_allwdcn_adur` int(11) NOT NULL DEFAULT 0 COMMENT '全部详单周期全天(0~23 点)_平均时长',
  `pa_l1mdtcn_adur` int(11) NOT NULL DEFAULT 0 COMMENT '近一月白天(8~18 点)_平均时长',
  `pa_l1mwdcn_adur` int(11) NOT NULL DEFAULT 0 COMMENT '近一月全天(0~23 点)_平均时长',
  `pa_allmncnrt_nums_con` int(11) NOT NULL DEFAULT 0 COMMENT '全部详单周期周末(周六周日)午夜(0~7 点)_详单中 的联系人总个数',
  `pa_l1wwdcnrt_nums_con` int(11) NOT NULL DEFAULT 0 COMMENT '近一周周末(周六周日)全天(0~23 点)_详单中的联系 人总个数',
  `pa_l2mwdcn_nums_con` int(11) NOT NULL DEFAULT 0 COMMENT '近两月全天(0~23 点)_详单中的联系人总个数',
  `pa_l2wwdcnwk_nums_con` int(11) NOT NULL DEFAULT 0 COMMENT '近两周工作日(周一~周五)全天(0~23 点)_详单中的 联系人总个数',
  `pa_allwdcn_a_dur_one_nums` int(11) NOT NULL DEFAULT 0 COMMENT '全部详单周期全天(0~23 点)_通话一次催收号码的 平均时长',
  `pa_l4mwdcn_a_dur_one_nums` int(11) NOT NULL DEFAULT 0 COMMENT '近四月全天(0~23 点)_通话一次催收号码的平均时 长',
  `pa_l6mwdcn_a_dur_one_nums` int(11) NOT NULL DEFAULT 0 COMMENT '近六月全天(0~23 点)_通话一次催收号码的平均时 长',
  `pa_l1mdtcnwk_rank_count` int(11) NOT NULL DEFAULT 0 COMMENT '近一月工作日(周一~周五)白天(8~18 点)_最高排名 比例',
  `pa_l1wdtcnrt_rank_count` int(11) NOT NULL DEFAULT 0 COMMENT '近一周周末(周六周日)白天(8~18 点)_最高排名比例',
  `pa_l2mdtcn_rank_count` int(11) NOT NULL DEFAULT 0 COMMENT '近两月白天(8~18 点)_最高排名比例',
  `pa_l3wwdcnwk_rank_count` int(11) NOT NULL DEFAULT 0 COMMENT '近三周工作日(周一~周五)全天(0~23 点)_最高排名 比例',
  `pa_l1mevcnrt_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近一月周末(周六周日)夜晚(19~23 点)_将一小时打 6 个及以上电话称为特殊小时，统计这种小时的数量',
  `pa_l1mmncnwk_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近一月工作日(周一~周五)午夜(0~7 点)_将一小时 打 6 个及以上电话称为特殊小时，统计这种小时的数 量',
  `pa_l1wevcn_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近一周夜晚(19~23 点)_将一小时打 6 个及以上电话 称为特殊小时，统计这种小时的数量',
  `pa_l1wwdcnrt_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近一周周末(周六周日)全天(0~23 点)_将一小时打 6 个及以上电话称为特殊小时，统计这种小时的数量',
  `pa_l2mevcn_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近两月夜晚(19~23 点)_将一小时打 6 个及以上电话 称为特殊小时，统计这种小时的数量',
  `pa_l2mevcnrt_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近两月周末(周六周日)夜晚(19~23 点)_将一小时打 6 个及以上电话称为特殊小时，统计这种小时的数量',
  `pa_l2mwdcn_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近两月全天(0~23 点)_将一小时打 6 个及以上电话称 为特殊小时，统计这种小时的数量',
  `pa_l3mevcn_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近三月夜晚(19~23 点)_将一小时打 6 个及以上电话 称为特殊小时，统计这种小时的数量',
  `pa_l3mwdcnrt_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近三月周末(周六周日)全天(0~23 点)_将一小时打 6 个及以上电话称为特殊小时，统计这种小时的数量',
  `pa_l6mwdcnwk_sepcial_hour_count` int(11) NOT NULL DEFAULT 0 COMMENT '近六月工作日(周一~周五)全天(0~23 点)_将一小时 打 6 个及以上电话称为特殊小时，统计这种小时的数 量',
  `pa_l2mdtcn_active_days_count` int(11) NOT NULL DEFAULT 0 COMMENT '近两月白天(8~18 点)_活跃的天数',
  `pa_l2wwdcn_active_days_count` int(11) NOT NULL DEFAULT 0 COMMENT '近两周全天(0~23 点)_活跃的天数',
  `pa_l3mwdcnrt_active_days_count` int(11) NOT NULL DEFAULT 0 COMMENT '近三月周末(周六周日)全天(0~23 点)_活跃的天数',
  `pa_l2wdtcn_max_times_in` int(11) NOT NULL DEFAULT 0 COMMENT '近两周白天(8~18 点)_被单个催收号码呼叫的最大 次数',
  `pa_l2wevcn_max_days_of_one_number_count` int(11) NOT NULL DEFAULT 0 COMMENT '近两周夜晚(19~23 点)_申请人联系一个号码的天数 的最大值',
  `pa_l3mwdcnrt_max_days_of_one_number_count` int(11) NOT NULL DEFAULT 0 COMMENT '近三月周末(周六周日)全天(0~23 点)_申请人联系一 个号码的天数的最大值',
  `pa_l3wwdcn_max_days_of_one_number_count` int(11) NOT NULL DEFAULT 0 COMMENT '近三周全天(0~23 点)_申请人联系一个号码的天数 的最大值',
  `pa_l2wwdcn_a_dur_one_nums_in` int(11) NOT NULL DEFAULT 0 COMMENT '近两周全天(0~23 点)_被催收号码呼叫一次催收号 码的平均时长',
  `pa_l2wwdcn_t_times_in` int(11) NOT NULL DEFAULT 0 COMMENT '近两周全天(0~23 点)_被催收号码呼叫次数',
  `pa_l3mwdcn_a_dur_in` int(11) NOT NULL DEFAULT 0 COMMENT '近三月全天(0~23 点)_被催收号码呼叫的平均时长',
  `pa_l1mevcnrt_t_dur` int(11) NOT NULL DEFAULT 0 COMMENT '近一月周末(周六周日)夜晚(19~23 点)_与催收号码 通话总时长',
  `pa_l1mmncnwk_t_dur` int(11) NOT NULL DEFAULT 0 COMMENT '近一月工作日(周一~周五)午夜(0~7 点)_与催收号 码通话总时长',
  `pa_l2mevcn_t_dur` int(11) NOT NULL DEFAULT 0 COMMENT '近两月夜晚(19~23 点)_与催收号码通话总时长',
  `pa_l2mwdcnrt_t_dur` int(11) NOT NULL DEFAULT 0 COMMENT '近两月周末(周六周日)全天(0~23 点)_与催收号码通 话总时长',
  `pa_l3mevcn_t_dur` int(11) NOT NULL DEFAULT 0 COMMENT '近三月夜晚(19~23 点)_与催收号码通话总时长',
  `pa_l3mwdcnrt_t_dur` int(11) NOT NULL DEFAULT 0 COMMENT '近三月周末(周六周日)全天(0~23 点)_与催收号码通 话总时长',
  `pa_l6mwdcnwk_t_dur` int(11) NOT NULL DEFAULT 0 COMMENT '近六月工作日(周一~周五)全天(0~23 点)_与催收号 码通话总时长',
  `pa_l4mwdcn_t_dur` int(11) NOT NULL DEFAULT 0 COMMENT '近四月全天(0~23 点)_与催收号码通话总时长',
  `pa_l6mwdcn_min_ttv_days_in` int(11) NOT NULL DEFAULT 0 COMMENT '近六月全天(0~23 点)_被催收号呼叫最小间隔天数',
  `pa_l6mwdcn_t_dur_f` int(11) NOT NULL DEFAULT 0 COMMENT '近六月全天(0~23 点)_与委外机构催收号呼叫的总 时长',
  `pa_all25acfri_dur` int(11) NOT NULL DEFAULT 0 COMMENT '周五的凌晨 2 点至 5 点通话时长',
  `pa_allwdacwk_dur` int(11) NOT NULL DEFAULT 0 COMMENT '周一至周五的通话时长',
  `pa_all25ac_times` int(11) NOT NULL DEFAULT 0 COMMENT '凌晨 2 点凌晨 5 点之间的通话次数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `last_modify_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `borrow_id` (`borrow_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='决策数据';