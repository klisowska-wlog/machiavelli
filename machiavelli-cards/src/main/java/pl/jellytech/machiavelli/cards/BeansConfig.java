package pl.jellytech.machiavelli.cards;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jellytech.machiavelli.cards.utils.MetricRegistryToolsTypes;

import java.util.function.Supplier;

@Configuration
public class BeansConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Beans that is used for collecting and calculating statistical metrics about technical data of application
    @Bean
    public MetricRegistry metricRegistry() {
        MetricRegistry mr = new MetricRegistry();
        mr.timer(MetricRegistryToolsTypes.RequestTimer.name());
        return mr;
    }
}
