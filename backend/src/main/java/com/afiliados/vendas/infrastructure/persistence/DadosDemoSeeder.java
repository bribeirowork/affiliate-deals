package com.afiliados.vendas.infrastructure.persistence;

import com.afiliados.vendas.domain.cupom.Cupom;
import com.afiliados.vendas.domain.cupom.TipoDesconto;
import com.afiliados.vendas.domain.oferta.Loja;
import com.afiliados.vendas.domain.oferta.Oferta;
import com.afiliados.vendas.infrastructure.persistence.repository.CupomSpringDataRepository;
import com.afiliados.vendas.infrastructure.persistence.repository.OfertaSpringDataRepository;
import com.afiliados.vendas.infrastructure.persistence.mapper.CupomPersistenceMapper;
import com.afiliados.vendas.infrastructure.persistence.mapper.OfertaPersistenceMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Popula dados de exemplo em ambiente de demonstração (H2 em memória).
 * Como o banco é em memória, ele reseta a cada subida da aplicação — este
 * seeder é o que garante que os mesmos produtos de teste apareçam sempre.
 */
@Component
public class DadosDemoSeeder implements CommandLineRunner {

    private final OfertaSpringDataRepository ofertaRepository;
    private final CupomSpringDataRepository cupomRepository;

    public DadosDemoSeeder(OfertaSpringDataRepository ofertaRepository, CupomSpringDataRepository cupomRepository) {
        this.ofertaRepository = ofertaRepository;
        this.cupomRepository = cupomRepository;
    }

    @Override
    public void run(String... args) {
        LocalDateTime agora = LocalDateTime.now();

        if (ofertaRepository.count() == 0) {
            criarOferta("Fone Bluetooth XYZ", "https://exemplo.com/fone.jpg", Loja.SHOPEE,
                    "89.90", "129.90", "https://shopee.com.br/afiliado/fone-xyz", agora, 6, 6);

            criarOferta("Smartwatch ABC", "https://exemplo.com/watch.jpg", Loja.MERCADO_LIVRE,
                    "249.90", "329.90", "https://mercadolivre.com.br/afiliado/watch-abc", agora, -2, 3);

            criarOferta("Fone de Ouvido Bluetooth Pro", "https://picsum.photos/seed/fone-pro/600/600", Loja.SHOPEE,
                    "79.90", "129.90", "https://shopee.com.br/afiliado/fone-pro", agora, 8, 6);

            criarOferta("Tênis Esportivo Runner", "https://picsum.photos/seed/tenis-runner/600/600", Loja.MAGAZINE_LUIZA,
                    "189.90", "259.90", "https://magazineluiza.com.br/afiliado/tenis-runner", agora, 10, 5);

            criarOferta("Caixa de Som Bluetooth Portátil", "https://picsum.photos/seed/caixa-som/600/600", Loja.TIKTOK_SHOP,
                    "59.90", null, "https://tiktokshop.com/afiliado/caixa-som", agora, 12, 4);

            criarOferta("Mochila Notebook Impermeável", "https://picsum.photos/seed/mochila-notebook/600/600", Loja.AMAZON,
                    "99.90", "149.90", "https://amazon.com.br/afiliado/mochila", agora, -2, 3);

            criarOferta("Notebook Ultra Slim 15\"", "https://picsum.photos/seed/notebook-slim/600/600", Loja.MERCADO_LIVRE,
                    "2199.00", "2799.00", "https://mercadolivre.com.br/afiliado/notebook-slim", agora, -4, 2);

            criarOferta("Cafeteira Elétrica Programável", "https://picsum.photos/seed/cafeteira/600/600", Loja.MAGAZINE_LUIZA,
                    "149.90", null, "https://magazineluiza.com.br/afiliado/cafeteira", agora, -1, 4);

            criarOferta("Óculos de Sol Polarizado",
                    "https://images.unsplash.com/photo-1572635196237-14b3f281503f?w=600&h=600&fit=crop", Loja.ALIEXPRESS,
                    "34.90", "59.90", "https://aliexpress.com/afiliado/oculos-sol", agora, 9, 7);

            criarOferta("Cadeira Gamer Ergonômica",
                    "https://images.unsplash.com/photo-1598550476439-6847785fcea6?w=600&h=600&fit=crop", Loja.SHOPEE,
                    "599.00", "899.00", "https://shopee.com.br/afiliado/cadeira-gamer", agora, -3, 3);

            criarOferta("Smart TV 50\" 4K",
                    "https://images.unsplash.com/photo-1587145820266-a5951ee6f620?w=600&h=600&fit=crop", Loja.MERCADO_LIVRE,
                    "1899.00", null, "https://mercadolivre.com.br/afiliado/smart-tv", agora, 6, 6);

            criarOferta("Air Fryer 5L Digital",
                    "https://images.unsplash.com/photo-1585659722983-3a675dabf23d?w=600&h=600&fit=crop", Loja.AMAZON,
                    "219.90", "299.90", "https://amazon.com.br/afiliado/air-fryer", agora, 11, 5);
        }

        if (cupomRepository.count() == 0) {
            // Cupom com link, sem valor mínimo: casa com TODAS as ofertas da Shopee.
            criarCupom("BEMVINDO10", Loja.SHOPEE, "https://shopee.com.br/afiliado/cupom",
                    TipoDesconto.PERCENTUAL, "10", null, agora, 1);

            // Cupom só de código, com valor mínimo de R$400: só casa com ofertas da Shopee
            // que atingem essa faixa (ex.: a Cadeira Gamer) — demonstra faixa de valor por cupom.
            criarCupom("SHOPEE50OFF", Loja.SHOPEE, null,
                    TipoDesconto.VALOR_FIXO, "50", "400", agora, 3);

            // Cupom só de código, sem valor mínimo: casa com TODAS as ofertas do Mercado Livre.
            criarCupom("FRETEGRATIS", Loja.MERCADO_LIVRE, null,
                    TipoDesconto.PERCENTUAL, "15", null, agora, 2);

            // Cupom com link, com valor mínimo de R$1500: só casa com ofertas do Mercado Livre
            // dessa faixa (Notebook e Smart TV), não com o Smartwatch (mais barato).
            criarCupom("FRETE300OFF", Loja.MERCADO_LIVRE, "https://mercadolivre.com.br/afiliado/cupom-premium",
                    TipoDesconto.VALOR_FIXO, "300", "1500", agora, 4);
        }
    }

    private void criarOferta(String nome, String imagemUrl, Loja loja, String valor, String valorOriginal,
                              String link, LocalDateTime agora, long offsetDiaHoras, long offsetSemanaDias) {
        Oferta oferta = Oferta.criar(nome, imagemUrl, null, loja,
                new BigDecimal(valor), valorOriginal != null ? new BigDecimal(valorOriginal) : null, link,
                agora.plusHours(offsetDiaHoras), agora.plusDays(offsetSemanaDias));

        ofertaRepository.save(OfertaPersistenceMapper.paraJpaEntity(oferta));
    }

    private void criarCupom(String codigo, Loja loja, String link, TipoDesconto tipo, String valorDesconto,
                             String valorMinimoCompra, LocalDateTime agora, long offsetDiasValidade) {
        Cupom cupom = Cupom.criar(codigo, loja, link, tipo, new BigDecimal(valorDesconto),
                valorMinimoCompra != null ? new BigDecimal(valorMinimoCompra) : null,
                agora.plusDays(offsetDiasValidade));

        cupomRepository.save(CupomPersistenceMapper.paraJpaEntity(cupom));
    }
}
