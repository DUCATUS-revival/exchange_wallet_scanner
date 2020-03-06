package io.mywish.btc.blockchain;

import com.neemre.btcdcli4j.core.client.BtcdClientImpl;
import io.lastwill.eventscan.model.NetworkType;
import io.lastwill.eventscan.repositories.LastBlockRepository;
import io.mywish.btc.blockchain.services.BtcNetwork;
import io.mywish.btc.blockchain.services.BtcScanner;
import io.mywish.scanner.services.LastBlockDbPersister;
import io.mywish.scanner.services.LastBlockPersister;
import org.apache.http.impl.client.CloseableHttpClient;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.TestNet3Params;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@ComponentScan
@Configuration
public class BtcBCModule {
    /**
     * Solution for test purposes only.
     * When we scan mainnet blocks we build addresses in mainnet format. And is not the same like testnet address.
     * This flag is solve the issue.
     */
    @Value("${etherscanner.bitcoin.treat-testnet-as-mainnet:false}")
    private boolean treatTestnetAsMainnet;

    @ConditionalOnProperty("etherscanner.bitcoin.rpc-url.mainnet")
    @Bean(name = NetworkType.BTC_MAINNET_VALUE)
    public BtcNetwork btcNetMain(
            final CloseableHttpClient closeableHttpClient,
            final @Value("${etherscanner.bitcoin.rpc-url.mainnet}") URI rpc
    ) throws Exception {
        String user = null, password = null;
        if (rpc.getUserInfo() != null) {
            String[] credentials = rpc.getUserInfo().split(":");
            if (credentials.length > 1) {
                user = credentials[0];
                password = credentials[1];
            }
        }
        return new BtcNetwork(
                NetworkType.BTC_MAINNET,
                new BtcdClientImpl(
                        closeableHttpClient,
                        rpc.getScheme(),
                        rpc.getHost(),
                        rpc.getPort(),
                        user,
                        password
                ),
                treatTestnetAsMainnet ? new TestNet3Params() : new MainNetParams()
        );
    }

    @Configuration
    public class DbPersisterConfiguration {
        @Bean
        public LastBlockPersister btcMainnetLastBlockPersister(
                LastBlockRepository lastBlockRepository,
                final @Value("${etherscanner.bitcoin.last-block.mainnet:#{null}}") Long lastBlock
        ) {
            return new LastBlockDbPersister(NetworkType.BTC_MAINNET, lastBlockRepository, lastBlock);
        }
    }

    @ConditionalOnBean(name = NetworkType.BTC_MAINNET_VALUE)
    @Bean
    public BtcScanner btcScannerMain(
            final @Qualifier(NetworkType.BTC_MAINNET_VALUE) BtcNetwork network,
            final @Qualifier("btcMainnetLastBlockPersister") LastBlockPersister lastBlockPersister,
            final @Value("${etherscanner.bitcoin.polling-interval-ms}") Long pollingInterval,
            final @Value("${etherscanner.bitcoin.commit-chain-length}") Integer commitmentChainLength
    ) {
        return new BtcScanner(
                network,
                lastBlockPersister,
                pollingInterval,
                commitmentChainLength
        );
    }
}
