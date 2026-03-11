package org.kursach.kursach.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import org.kursach.kursach.model.Region;
import org.kursach.kursach.model.BranchOffice;
import org.kursach.kursach.model.Representative;
import org.kursach.kursach.model.RepresentativeStatus;
import org.kursach.kursach.model.RepresentativeReport;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Logger;

@Named
@ApplicationScoped
public class DataInitializer {
    
    private static final Logger logger = Logger.getLogger(DataInitializer.class.getName());
    
    @Inject
    private EntityManager em;
    
    @PostConstruct
    public void init() {
        try {
            // Проверяем, есть ли уже данные
            Long regionCount = em.createQuery("SELECT COUNT(r) FROM Region r", Long.class).getSingleResult();
            
            if (regionCount == 0) {
                logger.info("Инициализация тестовых данных...");
                
                // Создаем 10 регионов
                Region[] regions = new Region[10];
                regions[0] = new Region("Московская область", "MO");
                regions[0].setCuratorName("Иванов Иван Иванович");
                regions[0].setContactPhone("+7 (495) 123-45-67");
                regions[0].setContactEmail("mo@company.ru");
                
                regions[1] = new Region("Ленинградская область", "LO");
                regions[1].setCuratorName("Петров Петр Петрович");
                regions[1].setContactPhone("+7 (812) 234-56-78");
                regions[1].setContactEmail("lo@company.ru");
                
                regions[2] = new Region("Краснодарский край", "KK");
                regions[2].setCuratorName("Сидорова Мария Ивановна");
                regions[2].setContactPhone("+7 (861) 345-67-89");
                regions[2].setContactEmail("kk@company.ru");
                
                regions[3] = new Region("Свердловская область", "SO");
                regions[3].setCuratorName("Смирнов Алексей Сергеевич");
                regions[3].setContactPhone("+7 (343) 456-78-90");
                regions[3].setContactEmail("so@company.ru");
                
                regions[4] = new Region("Новосибирская область", "NO");
                regions[4].setCuratorName("Козлова Елена Викторовна");
                regions[4].setContactPhone("+7 (383) 567-89-01");
                regions[4].setContactEmail("no@company.ru");
                
                regions[5] = new Region("Ростовская область", "RO");
                regions[5].setCuratorName("Волков Дмитрий Александрович");
                regions[5].setContactPhone("+7 (863) 678-90-12");
                regions[5].setContactEmail("ro@company.ru");
                
                regions[6] = new Region("Республика Татарстан", "RT");
                regions[6].setCuratorName("Новикова Анна Дмитриевна");
                regions[6].setContactPhone("+7 (843) 789-01-23");
                regions[6].setContactEmail("rt@company.ru");
                
                regions[7] = new Region("Челябинская область", "CO");
                regions[7].setCuratorName("Морозов Сергей Николаевич");
                regions[7].setContactPhone("+7 (351) 890-12-34");
                regions[7].setContactEmail("co@company.ru");
                
                regions[8] = new Region("Самарская область", "SA");
                regions[8].setCuratorName("Павлова Ольга Сергеевна");
                regions[8].setContactPhone("+7 (846) 901-23-45");
                regions[8].setContactEmail("sa@company.ru");
                
                regions[9] = new Region("Нижегородская область", "NO");
                regions[9].setCuratorName("Соколов Николай Владимирович");
                regions[9].setContactPhone("+7 (831) 012-34-56");
                regions[9].setContactEmail("no2@company.ru");
                
                em.getTransaction().begin();
                for (Region region : regions) {
                    em.persist(region);
                }
                em.getTransaction().commit();
                
                // Обновляем регионы, чтобы получить их ID
                em.clear();
                for (int i = 0; i < regions.length; i++) {
                    regions[i] = em.find(Region.class, regions[i].getId());
                }
                
                // Создаем 10 филиалов
                BranchOffice[] offices = new BranchOffice[10];
                offices[0] = new BranchOffice("Филиал Москва Центр", "Москва", regions[0]);
                offices[0].setAddress("ул. Тверская, д. 1");
                offices[0].setManagerName("Иванов И.И.");
                offices[0].setPhone("+7 (495) 111-11-11");
                offices[0].setOpenedDate(LocalDate.now().minusYears(5));
                
                offices[1] = new BranchOffice("Филиал Санкт-Петербург", "Санкт-Петербург", regions[1]);
                offices[1].setAddress("Невский проспект, д. 28");
                offices[1].setManagerName("Петров П.П.");
                offices[1].setPhone("+7 (812) 222-22-22");
                offices[1].setOpenedDate(LocalDate.now().minusYears(4));
                
                offices[2] = new BranchOffice("Филиал Краснодар", "Краснодар", regions[2]);
                offices[2].setAddress("ул. Красная, д. 15");
                offices[2].setManagerName("Сидорова М.И.");
                offices[2].setPhone("+7 (861) 333-33-33");
                offices[2].setOpenedDate(LocalDate.now().minusYears(3));
                
                offices[3] = new BranchOffice("Филиал Екатеринбург", "Екатеринбург", regions[3]);
                offices[3].setAddress("пр. Ленина, д. 50");
                offices[3].setManagerName("Смирнов А.С.");
                offices[3].setPhone("+7 (343) 444-44-44");
                offices[3].setOpenedDate(LocalDate.now().minusYears(6));
                
                offices[4] = new BranchOffice("Филиал Новосибирск", "Новосибирск", regions[4]);
                offices[4].setAddress("ул. Ленина, д. 12");
                offices[4].setManagerName("Козлова Е.В.");
                offices[4].setPhone("+7 (383) 555-55-55");
                offices[4].setOpenedDate(LocalDate.now().minusYears(2));
                
                offices[5] = new BranchOffice("Филиал Ростов-на-Дону", "Ростов-на-Дону", regions[5]);
                offices[5].setAddress("пр. Буденновский, д. 30");
                offices[5].setManagerName("Волков Д.А.");
                offices[5].setPhone("+7 (863) 666-66-66");
                offices[5].setOpenedDate(LocalDate.now().minusYears(4));
                
                offices[6] = new BranchOffice("Филиал Казань", "Казань", regions[6]);
                offices[6].setAddress("ул. Баумана, д. 58");
                offices[6].setManagerName("Новикова А.Д.");
                offices[6].setPhone("+7 (843) 777-77-77");
                offices[6].setOpenedDate(LocalDate.now().minusYears(3));
                
                offices[7] = new BranchOffice("Филиал Челябинск", "Челябинск", regions[7]);
                offices[7].setAddress("пр. Ленина, д. 75");
                offices[7].setManagerName("Морозов С.Н.");
                offices[7].setPhone("+7 (351) 888-88-88");
                offices[7].setOpenedDate(LocalDate.now().minusYears(5));
                
                offices[8] = new BranchOffice("Филиал Самара", "Самара", regions[8]);
                offices[8].setAddress("ул. Московское шоссе, д. 18");
                offices[8].setManagerName("Павлова О.С.");
                offices[8].setPhone("+7 (846) 999-99-99");
                offices[8].setOpenedDate(LocalDate.now().minusYears(2));
                
                offices[9] = new BranchOffice("Филиал Нижний Новгород", "Нижний Новгород", regions[9]);
                offices[9].setAddress("ул. Покровка, д. 7");
                offices[9].setManagerName("Соколов Н.В.");
                offices[9].setPhone("+7 (831) 000-00-00");
                offices[9].setOpenedDate(LocalDate.now().minusYears(4));
                
                em.getTransaction().begin();
                for (BranchOffice office : offices) {
                    em.persist(office);
                }
                em.getTransaction().commit();
                
                // Обновляем филиалы, чтобы получить их ID
                em.clear();
                for (int i = 0; i < offices.length; i++) {
                    offices[i] = em.find(BranchOffice.class, offices[i].getId());
                }
                for (int i = 0; i < regions.length; i++) {
                    regions[i] = em.find(Region.class, regions[i].getId());
                }
                
                // Создаем 10 представителей
                Representative[] representatives = new Representative[10];
                representatives[0] = new Representative("Иванов Иван Иванович", regions[0], offices[0]);
                representatives[0].setPositionTitle("Старший менеджер");
                representatives[0].setEmail("ivanov@company.ru");
                representatives[0].setPhone("+7 (495) 111-11-11");
                representatives[0].setStatus(RepresentativeStatus.ACTIVE);
                representatives[0].setExperienceYears(5);
                representatives[0].setSpecialization("Корпоративные продажи");
                
                representatives[1] = new Representative("Петров Петр Петрович", regions[1], offices[1]);
                representatives[1].setPositionTitle("Менеджер по продажам");
                representatives[1].setEmail("petrov@company.ru");
                representatives[1].setPhone("+7 (812) 222-22-22");
                representatives[1].setStatus(RepresentativeStatus.ACTIVE);
                representatives[1].setExperienceYears(3);
                representatives[1].setSpecialization("Работа с малым бизнесом");
                
                representatives[2] = new Representative("Сидорова Мария Ивановна", regions[2], offices[2]);
                representatives[2].setPositionTitle("Ведущий менеджер");
                representatives[2].setEmail("sidorova@company.ru");
                representatives[2].setPhone("+7 (861) 333-33-33");
                representatives[2].setStatus(RepresentativeStatus.ACTIVE);
                representatives[2].setExperienceYears(7);
                representatives[2].setSpecialization("Работа с крупными клиентами");
                
                representatives[3] = new Representative("Смирнов Алексей Сергеевич", regions[3], offices[3]);
                representatives[3].setPositionTitle("Менеджер по продажам");
                representatives[3].setEmail("smirnov@company.ru");
                representatives[3].setPhone("+7 (343) 444-44-44");
                representatives[3].setStatus(RepresentativeStatus.ON_LEAVE);
                representatives[3].setExperienceYears(2);
                representatives[3].setSpecialization("Работа с новыми клиентами");
                
                representatives[4] = new Representative("Козлова Елена Викторовна", regions[4], offices[4]);
                representatives[4].setPositionTitle("Старший менеджер");
                representatives[4].setEmail("kozlova@company.ru");
                representatives[4].setPhone("+7 (383) 555-55-55");
                representatives[4].setStatus(RepresentativeStatus.ACTIVE);
                representatives[4].setExperienceYears(6);
                representatives[4].setSpecialization("Технические решения");
                
                representatives[5] = new Representative("Волков Дмитрий Александрович", regions[5], offices[5]);
                representatives[5].setPositionTitle("Менеджер по продажам");
                representatives[5].setEmail("volkov@company.ru");
                representatives[5].setPhone("+7 (863) 666-66-66");
                representatives[5].setStatus(RepresentativeStatus.PROBATION);
                representatives[5].setExperienceYears(1);
                representatives[5].setSpecialization("Работа с малым бизнесом");
                
                representatives[6] = new Representative("Новикова Анна Дмитриевна", regions[6], offices[6]);
                representatives[6].setPositionTitle("Ведущий менеджер");
                representatives[6].setEmail("novikova@company.ru");
                representatives[6].setPhone("+7 (843) 777-77-77");
                representatives[6].setStatus(RepresentativeStatus.ACTIVE);
                representatives[6].setExperienceYears(8);
                representatives[6].setSpecialization("Корпоративные продажи");
                
                representatives[7] = new Representative("Морозов Сергей Николаевич", regions[7], offices[7]);
                representatives[7].setPositionTitle("Менеджер по продажам");
                representatives[7].setEmail("morozov@company.ru");
                representatives[7].setPhone("+7 (351) 888-88-88");
                representatives[7].setStatus(RepresentativeStatus.ACTIVE);
                representatives[7].setExperienceYears(4);
                representatives[7].setSpecialization("Работа с новыми клиентами");
                
                representatives[8] = new Representative("Павлова Ольга Сергеевна", regions[8], offices[8]);
                representatives[8].setPositionTitle("Старший менеджер");
                representatives[8].setEmail("pavlova@company.ru");
                representatives[8].setPhone("+7 (846) 999-99-99");
                representatives[8].setStatus(RepresentativeStatus.ACTIVE);
                representatives[8].setExperienceYears(5);
                representatives[8].setSpecialization("Технические решения");
                
                representatives[9] = new Representative("Соколов Николай Владимирович", regions[9], offices[9]);
                representatives[9].setPositionTitle("Менеджер по продажам");
                representatives[9].setEmail("sokolov@company.ru");
                representatives[9].setPhone("+7 (831) 000-00-00");
                representatives[9].setStatus(RepresentativeStatus.DISMISSED);
                representatives[9].setExperienceYears(0);
                representatives[9].setSpecialization("Работа с малым бизнесом");
                
                em.getTransaction().begin();
                for (Representative representative : representatives) {
                    em.persist(representative);
                }
                em.getTransaction().commit();
                
                // Обновляем представителей, чтобы получить их ID
                em.clear();
                for (int i = 0; i < representatives.length; i++) {
                    representatives[i] = em.find(Representative.class, representatives[i].getId());
                }
                
                // Создаем 10 отчетов представителей
                RepresentativeReport[] reports = new RepresentativeReport[10];
                reports[0] = new RepresentativeReport(representatives[0], 
                    LocalDate.now().minusMonths(1).withDayOfMonth(1),
                    LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                reports[0].setNewClients(5);
                reports[0].setSalesVolume(new BigDecimal("2500000.00"));
                reports[0].setMeetingsHeld(15);
                reports[0].setFocusProducts("Корпоративные решения, Облачные сервисы");
                reports[0].setKeyIssues("Увеличение спроса на облачные решения");
                reports[0].setSummary("Хорошие результаты месяца, превышен план на 15%");
                
                reports[1] = new RepresentativeReport(representatives[1],
                    LocalDate.now().minusMonths(1).withDayOfMonth(1),
                    LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                reports[1].setNewClients(8);
                reports[1].setSalesVolume(new BigDecimal("1800000.00"));
                reports[1].setMeetingsHeld(20);
                reports[1].setFocusProducts("Базовые решения для малого бизнеса");
                reports[1].setKeyIssues("Высокая конкуренция на рынке");
                reports[1].setSummary("Активная работа с новыми клиентами, план выполнен");
                
                reports[2] = new RepresentativeReport(representatives[2],
                    LocalDate.now().minusMonths(1).withDayOfMonth(1),
                    LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                reports[2].setNewClients(3);
                reports[2].setSalesVolume(new BigDecimal("5000000.00"));
                reports[2].setMeetingsHeld(12);
                reports[2].setFocusProducts("Крупные корпоративные проекты");
                reports[2].setKeyIssues("Длительный цикл продаж");
                reports[2].setSummary("Работа с крупными клиентами, превышен план на 25%");
                
                reports[3] = new RepresentativeReport(representatives[3],
                    LocalDate.now().minusMonths(2).withDayOfMonth(1),
                    LocalDate.now().minusMonths(2).withDayOfMonth(LocalDate.now().minusMonths(2).lengthOfMonth()));
                reports[3].setNewClients(10);
                reports[3].setSalesVolume(new BigDecimal("1200000.00"));
                reports[3].setMeetingsHeld(25);
                reports[3].setFocusProducts("Стартовые пакеты");
                reports[3].setKeyIssues("Необходимость обучения новым продуктам");
                reports[3].setSummary("Активная работа, план выполнен на 95%");
                
                reports[4] = new RepresentativeReport(representatives[4],
                    LocalDate.now().minusMonths(1).withDayOfMonth(1),
                    LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                reports[4].setNewClients(4);
                reports[4].setSalesVolume(new BigDecimal("3200000.00"));
                reports[4].setMeetingsHeld(18);
                reports[4].setFocusProducts("Технические решения, Интеграции");
                reports[4].setKeyIssues("Потребность в технической поддержке");
                reports[4].setSummary("Хорошие результаты, план выполнен на 110%");
                
                reports[5] = new RepresentativeReport(representatives[5],
                    LocalDate.now().minusMonths(1).withDayOfMonth(1),
                    LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                reports[5].setNewClients(6);
                reports[5].setSalesVolume(new BigDecimal("800000.00"));
                reports[5].setMeetingsHeld(12);
                reports[5].setFocusProducts("Базовые решения");
                reports[5].setKeyIssues("Адаптация к новым процессам");
                reports[5].setSummary("Первый месяц работы, план выполнен на 80%");
                
                reports[6] = new RepresentativeReport(representatives[6],
                    LocalDate.now().minusMonths(1).withDayOfMonth(1),
                    LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                reports[6].setNewClients(2);
                reports[6].setSalesVolume(new BigDecimal("4500000.00"));
                reports[6].setMeetingsHeld(10);
                reports[6].setFocusProducts("Корпоративные решения");
                reports[6].setKeyIssues("Работа с долгосрочными проектами");
                reports[6].setSummary("Отличные результаты, превышен план на 30%");
                
                reports[7] = new RepresentativeReport(representatives[7],
                    LocalDate.now().minusMonths(1).withDayOfMonth(1),
                    LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                reports[7].setNewClients(7);
                reports[7].setSalesVolume(new BigDecimal("2100000.00"));
                reports[7].setMeetingsHeld(22);
                reports[7].setFocusProducts("Стартовые пакеты, Расширенные решения");
                reports[7].setKeyIssues("Необходимость улучшения маркетинговых материалов");
                reports[7].setSummary("Активная работа, план выполнен");
                
                reports[8] = new RepresentativeReport(representatives[8],
                    LocalDate.now().minusMonths(1).withDayOfMonth(1),
                    LocalDate.now().minusMonths(1).withDayOfMonth(LocalDate.now().minusMonths(1).lengthOfMonth()));
                reports[8].setNewClients(5);
                reports[8].setSalesVolume(new BigDecimal("2800000.00"));
                reports[8].setMeetingsHeld(16);
                reports[8].setFocusProducts("Технические решения, Облачные сервисы");
                reports[8].setKeyIssues("Увеличение спроса на облачные решения");
                reports[8].setSummary("Хорошие результаты, план выполнен на 105%");
                
                reports[9] = new RepresentativeReport(representatives[9],
                    LocalDate.now().minusMonths(3).withDayOfMonth(1),
                    LocalDate.now().minusMonths(3).withDayOfMonth(LocalDate.now().minusMonths(3).lengthOfMonth()));
                reports[9].setNewClients(1);
                reports[9].setSalesVolume(new BigDecimal("500000.00"));
                reports[9].setMeetingsHeld(5);
                reports[9].setFocusProducts("Базовые решения");
                reports[9].setKeyIssues("Низкая активность");
                reports[9].setSummary("План не выполнен, низкие результаты");
                
                em.getTransaction().begin();
                for (RepresentativeReport report : reports) {
                    em.persist(report);
                }
                em.getTransaction().commit();
                
                logger.info("Тестовые данные успешно инициализированы");
            } else {
                logger.info("База данных уже содержит данные, инициализация пропущена");
            }
        } catch (Exception e) {
            logger.severe("Ошибка при инициализации тестовых данных: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
