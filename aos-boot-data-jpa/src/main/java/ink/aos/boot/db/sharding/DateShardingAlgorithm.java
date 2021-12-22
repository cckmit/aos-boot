package ink.aos.boot.db.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;
import java.util.Date;

@Slf4j
public class DateShardingAlgorithm implements PreciseShardingAlgorithm<Date> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Date> shardingValue) {

        log.debug("table PreciseShardingAlgorithm ");
        // 真实节点
        availableTargetNames.stream().forEach((item) -> {
            log.debug("actual node table:{}", item);
        });

        log.debug("logic table name:{},rout column:{}", shardingValue.getLogicTableName(), shardingValue.getColumnName());

        //精确分片
        log.debug("column value:{}", shardingValue.getValue());

        String tb_name = shardingValue.getLogicTableName() + "_";

        // 根据当前日期 来 分库分表
        Date date = shardingValue.getValue();
        String year = String.format("%tY", date);
        String mon = String.valueOf(Integer.parseInt(String.format("%tm", date))); // 去掉前缀0
        String dat = String.format("%td", date);

        // 选择表
        tb_name = tb_name + year + mon;
        log.debug("tb_name:" + tb_name);
        for (String each : availableTargetNames) {
            log.debug("t_order_:" + each);
            if (each.equals(tb_name)) {
                return each;
            }
        }

        throw new IllegalArgumentException();
    }

}
