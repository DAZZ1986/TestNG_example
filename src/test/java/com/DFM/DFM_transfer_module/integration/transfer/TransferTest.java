package com.DFM.DFM_transfer_module.integration.transfer;

import com.DFM.DFM_transfer_module.model.Club;
import com.DFM.DFM_transfer_module.model.Player;
import com.DFM.DFM_transfer_module.service.ClubService;
import com.DFM.DFM_transfer_module.service.PlayerService;
import com.DFM.DFM_transfer_module.service.TransferService;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;
import static com.DFM.DFM_transfer_module.service.SqlRequest.*;
import static java.sql.DriverManager.getConnection;

//@SpringBootTest(classes = DfmTransferModuleApplication.class)
@SpringBootTest
@Epic("Transfer Operations")
@Feature("Player Transfer")
public class TransferTest extends BaseTest {

    @Autowired // или другая аннотация для инъекции
    private ClubService clubService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private TransferService transferService;



    public TransferTest() {
        // конструктор без параметров
    }

/*
    public TransferTest(ClubService clubService, PlayerService playerService, TransferService transferService) {
        this.clubService = clubService;
        this.playerService = playerService;
        this.transferService = transferService;
    }
*/
/*
1. Основной позитивный случай (минимум данных).
	Тест-кейс 1: "Успешный трансфер":
		Шаг																ТД								Ожидаемый результат
		- отправлен POST запрос с фронта на покупку игрока				игрок с id 6					отправлен POST запрос с заполненным в параметрах урл: clubId, playerId.
		- отправлен POST запрос с трансфер модуля в брокер модуль		json объект						получен ответ 200, в теле сообщения json: clubId, playerId, transferRequest true.
		- брокер модуль отправил сообщение в RabbitMQ					json объект						json сообщение поступило в очередь RabbitMQ.
		- трансфер модуль вычитал сообщение из RabbitMQ													в консоли наблюдаем сообщениме "RabbitMQ_Json_Consumer:  Received JSON message -> ...".
		- смена id клуба у игрока																		в таблице Player у записи с id 6, заполнилось поле clubId, id клуба из шага 1.
		- у клуба были вычтены деньги за игрока															в таблице Club у записи клуба бюджет клуба уменьшился на стоимость игрока. SELECT balance FROM public.club WHERE id = {clubId}
 */
/*
    //Jpa - not working
    @Test(groups = "smoke")
    @Transactional
    public void happyTransferTest() throws InterruptedException {
        // 1. Получаем данные
        Club club = clubService.findById(2L);
        Player player = playerService.findById(1L);

        //- у клуба были вычтены деньги за игрока
        int clubBalanceBeforeTransfer = club.getBalance();
        int playerPrice = player.getPrice();

        // Явно инициализируем ленивые поля ДО выполнения операций
        Hibernate.initialize(club);
        Hibernate.initialize(player);

        // 3. Выполняем операцию
        transferService.transferRequestOperation(2L, 1L);
        Thread.sleep(10000);

        // 4. Проверяем результаты
        //- смена id клуба у игрока
        //Long playerClubId = player.getClub().getId();     //jpa
        //Assert.assertEquals(playerClubId, 2);             //jpa

        //- у клуба были вычтены деньги за игрока
        int actualClubBalanceAfterTransfer = clubBalanceBeforeTransfer - playerPrice;
        Assert.assertEquals(actualClubBalanceAfterTransfer, 50505050);
    }
*/
    //JDBC - ok
    @Test(groups = "smoke")
    @Story("Successful Transfer")
    @Description("Verify that player transfer between clubs works correctly")
    @Transactional
    public void happyTransferTest() throws InterruptedException {
        // 1. Получаем данные
        Club club = clubService.findById(2L);           //Real Madrid
        Player player = playerService.findById(1L);     //Zidane
        //jdbc    вызов метода получения данных через sql запрос к БД(баланс клуба до трансфера)
        int clubBalanceBeforeTransfer = getClubBalance(club.getId(), getSqlHelper());   //вызвал метод getClubBalance() через импорт статического класса
        //вызвал метод getSqlHelper() через наследование от класса extends BaseTest и через данный геттер метод прокинул внутрь переменную sqlHelper

        // 2. Выполняем операцию
        transferService.transferRequestOperation(club.getId(), player.getId());

        // 3. Asserts
        // 3.1 смена id клуба у игрока
        Thread.sleep(1000);         //for rabbit, consumer waiting
        //public static void assertEquals(long actual, long expected, String message)           //jdbc    вызов метода получения данных через sql запрос к БД
        Assert.assertEquals(getPlayerClubId(player.getId(), getSqlHelper()), club.getId());     //вызвал метод getPlayerClubId() через импорт статического класса
        //вызвал метод getSqlHelper() через наследование от класса extends BaseTest и через данный геттер метод прокинул внутрь переменную sqlHelper

        // 3.2 у клуба были вычтены деньги за игрока
        int playerPrice = player.getPrice();
        int expectedClubBalanceAfterTransfer = clubBalanceBeforeTransfer - playerPrice;
        Assert.assertEquals(getClubBalance(club.getId(), getSqlHelper()), expectedClubBalanceAfterTransfer);
    }

    /*
    @Test(groups = "smoke")
    @Story("Successful Transfer")
    @Description("Verify that player transfer between clubs works correctly")
    public void happyTransferTest() {
        // 1. Get initial data
        Club club = clubService.findById(2L);
        Player player = playerService.findById(1L);
        int clubBalanceBeforeTransfer = clubService.getClubBalance(club.getId());
        int playerPrice = player.getPrice();

        // 2. Perform transfer
        transferService.transferRequestOperation(club.getId(), player.getId());

        // 3. Verify results with awaitility
        await().atMost(5, SECONDS).untilAsserted(() -> {
            // Verify player's new club
            Player updatedPlayer = playerService.findById(player.getId());
            Assert.assertEquals(updatedPlayer.getClubId(), club.getId());

            // Verify club balance
            Club updatedClub = clubService.findById(club.getId());
            int expectedBalance = clubBalanceBeforeTransfer - playerPrice;
            Assert.assertEquals(updatedClub.getBalance(), expectedBalance);
        });
    }
*/
}
