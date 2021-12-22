package ink.aos.boot.db.sharding;


import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

@Slf4j
public class DBShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {

        log.debug("DB  PreciseShardingAlgorithm  ");
        // 真实节点
        availableTargetNames.stream().forEach((item) -> {
            log.debug("actual node db:{}", item);
        });

        log.debug("logic table name:{},rout column:{}", shardingValue.getLogicTableName(), shardingValue.getColumnName());

        //精确分片
        log.debug("column value:{}", shardingValue.getValue());

        String orderId = shardingValue.getValue();

        long db_index = orderId.hashCode() & (2 - 1);

        for (String each : availableTargetNames) {
            if (each.equals("b" + db_index)) {
                return each;
            }
        }

        throw new IllegalArgumentException();
    }

}