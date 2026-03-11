-- ============================================
-- SQL ЗАПРОСЫ ДЛЯ ПОЛУЧЕНИЯ ДАННЫХ ИЗ БД
-- База данных: kursach_stas
-- ============================================

-- ============================================
-- 1. ПРОСТЫЕ SELECT ЗАПРОСЫ
-- ============================================

-- Получить все регионы
SELECT * FROM region;

-- Получить все филиалы
SELECT * FROM branch_office;

-- Получить всех представителей
SELECT * FROM representative;

-- Получить все отчеты
SELECT * FROM representative_report;

-- ============================================
-- 2. ЗАПРОСЫ С ФИЛЬТРАЦИЕЙ (WHERE)
-- ============================================

-- Получить активных представителей
SELECT * FROM representative 
WHERE status = 'ACTIVE';

-- Получить филиалы в конкретном городе
SELECT * FROM branch_office 
WHERE city = 'Москва';

-- Получить представителей с опытом более 5 лет
SELECT * FROM representative 
WHERE experience_years > 5;

-- Получить отчеты с объемом продаж более 2 миллионов
SELECT * FROM representative_report 
WHERE sales_volume > 2000000;

-- Получить регионы по коду
SELECT * FROM region 
WHERE region_code = 'MO';

-- ============================================
-- 3. ЗАПРОСЫ С СОРТИРОВКОЙ (ORDER BY)
-- ============================================

-- Получить регионы, отсортированные по названию
SELECT * FROM region 
ORDER BY name;

-- Получить представителей, отсортированных по опыту (от большего к меньшему)
SELECT * FROM representative 
ORDER BY experience_years DESC;

-- Получить отчеты, отсортированные по объему продаж
SELECT * FROM representative_report 
ORDER BY sales_volume DESC;

-- Получить филиалы, отсортированные по дате открытия
SELECT * FROM branch_office 
ORDER BY opened_date DESC;

-- ============================================
-- 4. ЗАПРОСЫ С JOIN (СВЯЗИ МЕЖДУ ТАБЛИЦАМИ)
-- ============================================

-- Получить филиалы с информацией о регионе
SELECT 
    bo.id,
    bo.title,
    bo.city,
    bo.address,
    bo.manager_name,
    bo.phone,
    bo.opened_date,
    r.name AS region_name,
    r.region_code,
    r.curator_name AS region_curator
FROM branch_office bo
LEFT JOIN region r ON bo.region_id = r.id
ORDER BY r.name, bo.city;

-- Получить представителей с информацией о регионе и филиале
SELECT 
    rep.id,
    rep.full_name,
    rep.position_title,
    rep.email,
    rep.phone,
    rep.status,
    rep.experience_years,
    rep.specialization,
    r.name AS region_name,
    bo.title AS branch_office_title,
    bo.city AS branch_city
FROM representative rep
LEFT JOIN region r ON rep.region_id = r.id
LEFT JOIN branch_office bo ON rep.branch_office_id = bo.id
ORDER BY r.name, rep.full_name;

-- Получить отчеты с информацией о представителе
SELECT 
    rr.id,
    rr.period_start,
    rr.period_end,
    rr.new_clients,
    rr.sales_volume,
    rr.meetings_held,
    rr.focus_products,
    rr.key_issues,
    rr.summary,
    rep.full_name AS representative_name,
    rep.position_title,
    rep.email AS representative_email
FROM representative_report rr
LEFT JOIN representative rep ON rr.representative_id = rep.id
ORDER BY rr.period_end DESC, rr.sales_volume DESC;

-- Получить полную информацию об отчетах (с представителем, регионом и филиалом)
SELECT 
    rr.id AS report_id,
    rr.period_start,
    rr.period_end,
    rr.new_clients,
    rr.sales_volume,
    rr.meetings_held,
    rr.focus_products,
    rr.key_issues,
    rr.summary,
    rep.full_name AS representative_name,
    rep.position_title,
    rep.status AS representative_status,
    r.name AS region_name,
    bo.title AS branch_office_title,
    bo.city AS branch_city
FROM representative_report rr
LEFT JOIN representative rep ON rr.representative_id = rep.id
LEFT JOIN region r ON rep.region_id = r.id
LEFT JOIN branch_office bo ON rep.branch_office_id = bo.id
ORDER BY rr.period_end DESC;

-- ============================================
-- 5. ЗАПРОСЫ С АГРЕГАЦИЕЙ (GROUP BY, COUNT, SUM, AVG)
-- ============================================

-- Количество представителей по статусам
SELECT 
    status,
    COUNT(*) AS count
FROM representative
GROUP BY status
ORDER BY count DESC;

-- Количество филиалов по регионам
SELECT 
    r.name AS region_name,
    COUNT(bo.id) AS branch_count
FROM region r
LEFT JOIN branch_office bo ON r.id = bo.region_id
GROUP BY r.id, r.name
ORDER BY branch_count DESC;

-- Общий объем продаж по регионам
SELECT 
    r.name AS region_name,
    SUM(rr.sales_volume) AS total_sales,
    COUNT(rr.id) AS report_count,
    AVG(rr.sales_volume) AS avg_sales
FROM region r
LEFT JOIN representative rep ON r.id = rep.region_id
LEFT JOIN representative_report rr ON rep.id = rr.representative_id
GROUP BY r.id, r.name
HAVING SUM(rr.sales_volume) IS NOT NULL
ORDER BY total_sales DESC;

-- Статистика по представителям (количество отчетов, общий объем продаж)
SELECT 
    rep.id,
    rep.full_name,
    rep.position_title,
    COUNT(rr.id) AS report_count,
    SUM(rr.sales_volume) AS total_sales,
    AVG(rr.sales_volume) AS avg_sales,
    SUM(rr.new_clients) AS total_new_clients,
    SUM(rr.meetings_held) AS total_meetings
FROM representative rep
LEFT JOIN representative_report rr ON rep.id = rr.representative_id
GROUP BY rep.id, rep.full_name, rep.position_title
ORDER BY total_sales DESC NULLS LAST;

-- Средний объем продаж по специализациям
SELECT 
    rep.specialization,
    COUNT(DISTINCT rep.id) AS representative_count,
    COUNT(rr.id) AS report_count,
    AVG(rr.sales_volume) AS avg_sales_volume,
    SUM(rr.sales_volume) AS total_sales_volume
FROM representative rep
LEFT JOIN representative_report rr ON rep.id = rr.representative_id
WHERE rep.specialization IS NOT NULL
GROUP BY rep.specialization
ORDER BY avg_sales_volume DESC NULLS LAST;

-- ============================================
-- 6. ЗАПРОСЫ С УСЛОВИЯМИ И ФИЛЬТРАЦИЕЙ
-- ============================================

-- Представители с опытом более 5 лет и статусом ACTIVE
SELECT * FROM representative 
WHERE experience_years > 5 
  AND status = 'ACTIVE'
ORDER BY experience_years DESC;

-- Отчеты за последний месяц (пример)
SELECT 
    rr.*,
    rep.full_name AS representative_name
FROM representative_report rr
LEFT JOIN representative rep ON rr.representative_id = rep.id
WHERE rr.period_end >= CURRENT_DATE - INTERVAL '1 month'
ORDER BY rr.period_end DESC;

-- Филиалы, открытые в последние 2 года
SELECT * FROM branch_office 
WHERE opened_date >= CURRENT_DATE - INTERVAL '2 years'
ORDER BY opened_date DESC;

-- Топ-5 представителей по объему продаж
SELECT 
    rep.full_name,
    rep.position_title,
    SUM(rr.sales_volume) AS total_sales
FROM representative rep
LEFT JOIN representative_report rr ON rep.id = rr.representative_id
GROUP BY rep.id, rep.full_name, rep.position_title
HAVING SUM(rr.sales_volume) IS NOT NULL
ORDER BY total_sales DESC
LIMIT 5;

-- ============================================
-- 7. ЗАПРОСЫ С ПОДЗАПРОСАМИ
-- ============================================

-- Представители, у которых есть отчеты с объемом продаж более 3 миллионов
SELECT * FROM representative 
WHERE id IN (
    SELECT DISTINCT representative_id 
    FROM representative_report 
    WHERE sales_volume > 3000000
);

-- Регионы, в которых есть активные представители
SELECT * FROM region 
WHERE id IN (
    SELECT DISTINCT region_id 
    FROM representative 
    WHERE status = 'ACTIVE' 
      AND region_id IS NOT NULL
);

-- ============================================
-- 8. ЗАПРОСЫ ДЛЯ ПОИСКА (LIKE, ILIKE)
-- ============================================

-- Поиск представителей по имени (регистр не важен)
SELECT * FROM representative 
WHERE full_name ILIKE '%Иванов%'
ORDER BY full_name;

-- Поиск филиалов по городу
SELECT * FROM branch_office 
WHERE city ILIKE '%Москва%'
   OR city ILIKE '%Санкт%';

-- Поиск по специализации
SELECT * FROM representative 
WHERE specialization ILIKE '%корпоратив%'
ORDER BY full_name;

-- ============================================
-- 9. ЗАПРОСЫ С ОГРАНИЧЕНИЕМ (LIMIT)
-- ============================================

-- Первые 10 представителей
SELECT * FROM representative 
ORDER BY id 
LIMIT 10;

-- Последние 5 отчетов
SELECT * FROM representative_report 
ORDER BY period_end DESC 
LIMIT 5;

-- ============================================
-- 10. ЗАПРОСЫ С ВЫЧИСЛЯЕМЫМИ ПОЛЯМИ
-- ============================================

-- Представители с расчетом эффективности (объем продаж / количество встреч)
SELECT 
    rep.full_name,
    rep.position_title,
    SUM(rr.sales_volume) AS total_sales,
    SUM(rr.meetings_held) AS total_meetings,
    CASE 
        WHEN SUM(rr.meetings_held) > 0 
        THEN ROUND(SUM(rr.sales_volume) / SUM(rr.meetings_held), 2)
        ELSE 0 
    END AS sales_per_meeting
FROM representative rep
LEFT JOIN representative_report rr ON rep.id = rr.representative_id
GROUP BY rep.id, rep.full_name, rep.position_title
HAVING SUM(rr.sales_volume) IS NOT NULL
ORDER BY sales_per_meeting DESC;

-- Длительность периода отчета в днях
SELECT 
    id,
    period_start,
    period_end,
    (period_end - period_start) AS period_days,
    new_clients,
    sales_volume
FROM representative_report
WHERE period_start IS NOT NULL 
  AND period_end IS NOT NULL
ORDER BY period_end DESC;



