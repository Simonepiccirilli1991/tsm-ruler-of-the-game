package com.tsm.rule.reseller;

import com.tsm.rule.reseller.io.request.CartePokemonRequest;
import com.tsm.rule.reseller.reactivewrapper.PokemonWrapperService;
import com.tsm.rule.reseller.service.CartePokemonService;
import com.tsm.rule.reseller.utils.TipiProdotto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@SpringBootTest
class ResellerMsApplicationTests {

	@Autowired
	PokemonWrapperService pokemonWrapperService;
	@Autowired
	CartePokemonService cartePokemonService;


	@Test
	void saveCartaAndGetServiceTestOK() {

		var request = new CartePokemonRequest("Etb scintille folgoranti", LocalDateTime.now(),200.00,4,
				"1233gg", TipiProdotto.PRODOTTO_SEALED,true,false,false,null,"online");

		var entity = cartePokemonService.saveCartaPokemon(request);

		Assertions.assertTrue(!ObjectUtils.isEmpty(entity.getChiaveOggetto()));
		var chiave = entity.getChiaveOggetto();

		var resp = cartePokemonService.getCartaPokemonChiave(chiave);

		Assertions.assertTrue("online".equals(resp.getAcquistatoPresso()));
	}

}
