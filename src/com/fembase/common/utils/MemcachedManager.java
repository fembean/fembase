package com.fembase.common.utils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.rubyeye.xmemcached.Counter;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fembase.common.config.IdMapping;
import com.fembase.common.config.McName;
import com.fembase.common.db.DbManager;
import com.fembase.common.db.ScalerObjectBuilder;
import com.fembase.modules.signin.entity.TokenInfo;


public class MemcachedManager {
	protected Log log = LogFactory.getLog(getClass());
    private HashMap<String,MemcachedClient> clientMap;
    private MemcachedManager() {
        clientMap = new HashMap<String,MemcachedClient>();
    }
    
    public static final MemcachedManager instance = new MemcachedManager();
    
    public MemcachedClient getMemClient(String mcName) {
        return clientMap.get(mcName);
    }
    
    public void destroyAll() {
        for(MemcachedClient mc : clientMap.values()) {
            if(!mc.isShutdown()) {
                try {
                    mc.shutdown();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void init() {
        try {
            String[] strings = DbManager.getInstnace().executeScalerObject("select `mcname`,`url` from as_mcsource", null, ScalerObjectBuilder.stringArrayBuilder);
            MemcachedClient mc = buildClient(strings[1]);
            clientMap.put(strings[0], mc);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
 
    // ========== facebook validation ===========
    public void setOpenUserInfo(String uid,TokenInfo tokeninfo) {
        String cacheKey = String.format("jduf_%s",uid);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            mc.set(cacheKey, 1200, tokeninfo);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("error", e);
        }
    }
    
    public TokenInfo getOpenUserInfo(String uid) {
        String cacheKey = String.format("jduf_%s",uid);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return null;
        }
    }
    
    // ========= task =======
    
    public String getTodayTaskIds(int jodouid,String puid) {
        String strToday = DateUtil.formatYMD(new Date());
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        try {
            String key = String.format("task_td_id_%s_%s",strToday, jodouid);
            String v = (String)mc.get(key);
            if(v == null) {
                return "";
            }
            return v;
        } catch (Exception e) {
            log.error("", e);
            return "";
        } 
    }
    
    public boolean setTodayTaskIds(int jodouid, String puid, String taskIds) {
        String strToday = DateUtil.formatYMD(new Date());
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        try {
            String key = String.format("task_td_id_%s_%s",strToday, jodouid);
            return mc.set(key, 86400, taskIds);
        } catch (Exception e) {
           log.error("error", e);
            return false;
        } 
    }
    
    // ========= 高价task =======
    public String getTodayHighPriceTaskIds(int jodouid) {
        String strToday = DateUtil.formatYMD(new Date());
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        try {
        	String key = String.format("highPriceTask_td_id_%s_%s",strToday, jodouid);
            String v = (String)mc.get(key);
            if(v == null) {
                return "";
            }
            return v;
        } catch (Exception e) {
            log.error("", e);
            return "";
        } 
    }
    
    public boolean setTodayHighPriceTaskIds(int jodouid, String taskIds) {
        String strToday = DateUtil.formatYMD(new Date());
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        try {
            String key = String.format("highPriceTask_td_id_%s_%s",strToday, jodouid);
            return mc.set(key, 1200, taskIds);
        } catch (Exception e) {
           log.error("", e);
            return false;
        } 
    }
    // ========= 游戏 =======
    public String getTodayGameIds(int jodouid) {
        String strToday = DateUtil.formatYMD(new Date());
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        try {
        	String key = String.format("game_gd_id_%s_%s",strToday, jodouid);
            String v = (String)mc.get(key);
            if(v == null) {
                return "";
            }
            return v;
        } catch (Exception e) {
            log.error("", e);
            return "";
        } 
    }
    
    public boolean setTodayGameIds(int jodouid, String content) {
        String strToday = DateUtil.formatYMD(new Date());
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        try {
            String key = String.format("game_gd_id_%s_%s",strToday, jodouid);
            return mc.set(key, 86400, content);
        } catch (Exception e) {
            log.error("", e);
            return false;
        } 
    }
    
    // 每日任務計數
    public long getTodayTaskDoneCount(int jodouid,String puid) {
        String strToday = DateUtil.formatYMD(new Date());
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        try {
            String key = String.format("task_td_dc_%s_%s",strToday, jodouid);
            Counter counter = mc.getCounter(key);
            if(counter == null) {
                return 0;
            }
            return counter.get();
        } catch (Exception e) {
           log.error("", e);
            return 0;
        } 
    }
    
    public long incTodayTaskDoneCount(int jodouid,String puid, int inc) {
        String strToday = DateUtil.formatYMD(new Date());
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        String key = String.format("task_td_dc_%s_%s",strToday, jodouid);
        try {
            return mc.incr(key, inc, 0, 2000, 86400);
        } catch (Exception e) {
           log.error("", e);
            return -1L;
        }
    }
    
    //每日签到
    public String getSignInByUid(String uid){
    	MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        String key = String.format("sign_in_%s", uid);

        try {
            String v = (String)mc.get(key);
            return v;
        } catch (Exception e) {
           log.error("", e);
            return null;
        } 
    }
    
    public void setSignInByJodouid(int jodouid,String signin_info){
    	MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        String key = String.format("sign_in_%s", jodouid);
        try {
            mc.set(key, 86400, signin_info);
        } catch (Exception e) {
        	log.error("", e);
        } 
    }
    
    /*====fyber======*/
    public void setFyberTransId(String tansid) {
        String cacheKey = String.format("fyber_transid_%s",tansid);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            mc.set(cacheKey, 24*60*60, tansid);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }
    
    public String getFyberTransId(String tansid) {
        String cacheKey = String.format("fyber_transid_%s",tansid);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return null;
        }
    }
    
    /*====appdriver======*/
    public void setAppDriverAchieveId(String achieve_id) {
        String cacheKey = String.format("appdriver_achieve_id_%s",achieve_id);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            mc.set(cacheKey, 24*60*60, achieve_id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }
    
    public String getAppDriverAchieveId(String achieve_id) {
        String cacheKey = String.format("appdriver_achieve_id_%s",achieve_id);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return null;
        }
    }
    /*appdriver end*/
    
    /*========OnlineOfferWall===========*/
    public void setOnlineOfferWall(int achieveId){
    	String cacheKey = String.format("onlineOfferWall_achieveId_%s",achieveId);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            mc.set(cacheKey, 24*60*60, achieveId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }
    public int getOnlineOfferWall(int achieveId){
    	String cacheKey = String.format("onlineOfferWall_achieveId_%s",achieveId);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(cacheKey);
        } catch (Exception e) {
            return 0;
        }
    }
    
    /*====end OnlineOfferWall=======*/
    
    /*====youmi任務訂單號======*/
    public void setYoumiOrderId(String order) {
        String cacheKey = String.format("youmi_storeid_%s",order);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            mc.set(cacheKey, 24*60*60, order);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }
    
    public String getYoumiOrderId(String order) {
        String cacheKey = String.format("youmi_order_%s",order);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return null;
        }
    }
    
    //gameuid与jodouid映射
    public void setJodouid(String gameuid,IdMapping idMapping){
    	String cacheKey = String.format("jodouid_gameuid_puid_%s",gameuid);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            mc.set(cacheKey, 24*60*60, idMapping);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }
    
    public IdMapping getJoduid(String gameuid){
    	String cacheKey = String.format("jodouid_gameuid_puid_%s",gameuid);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return null;
        }
    }

    
    //puid,idfa 獲取任務的次數
	public void setTaskTime(String puid,String value){
		String cacheKey = String.format("puid_idfa_getTaskTime_%s",puid);
	    MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
	    try {
	        mc.set(cacheKey, 24*60*60, value);
	    } catch (Exception e) {
	        e.printStackTrace();
	        log.error("", e);
	    }
	}
	public String getTaskTime(String puid){
		String cacheKey = String.format("puid_idfa_getTaskTime_%s",puid);
		MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return null;
        }
	}
	public void deleteTaskTime(String puid){
		String cacheKey = String.format("puid_idfa_getTaskTime_%s",puid);
		MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
		try {
			mc.delete(cacheKey);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("", e);
		}
	}
	//fyber clickTime,callbackTime
	public void setClickAndCallbackTime(String country,String value,String pkg){
		String strToday = DateUtil.formatYMD(new Date());
		String cacheKey = String.format("fyber_time_%s_%s_%s",strToday,country,pkg);
		//String cacheKey = String.format("fyber_clickAndCallback_time");
	    MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
	    try {
	        mc.set(cacheKey, 24*60*60, value);
	    } catch (Exception e) {
	        e.printStackTrace();
	        log.error("", e);
	    }
	}
	public String getClickAndCallbackTime(String country,String pkg){
		String strToday = DateUtil.formatYMD(new Date());
		String cacheKey = String.format("fyber_time_%s_%s_%s",strToday,country,pkg);
		MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(cacheKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return null;
        }
	}
    
    //ip地址對應的的NHT值
    public void setIpNHT(String ip,String NHT){
    	String strToday = DateUtil.formatYMD(new Date());
        String key = String.format("ip_NHT_%s_%s",strToday, ip);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            mc.set(key, 24*60*60, NHT);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
        }
    }
    
    public String getIpNHT(String ip){
    	String strToday = DateUtil.formatYMD(new Date());
        String key = String.format("ip_NHT_%s_%s",strToday, ip);
        MemcachedClient mc = MemcachedManager.instance.getMemClient(McName.FREE_MONEY);
        try {
            return mc.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("", e);
            return null;
        }
    }
    
    /*y end*/
    
    public static void main(String[] args) {
        ConfigManager.instance.init();
        
        long todayTaskDoneCount = MemcachedManager.instance.getTodayTaskDoneCount(10001, "1111");
        System.out.println(todayTaskDoneCount);
    }
    
    
    public String getPuidByJodouid(int jodouid) {//一個puid可能對應多個jodouid
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        String key = String.format("task_pi_ji_%s", jodouid);

        try {
            String v = (String)mc.get(key);
            return v;
        } catch (Exception e) {
        	log.error("", e);
            return null;
        } 
    }
    
    public void setPuidJodoMap(int jodouid,String puid) {
        MemcachedClient mc = getMemClient(McName.FREE_MONEY);
        String key = String.format("task_pi_ji_%s", puid);
        try {
            mc.set(key, 36000, jodouid);
        } catch (Exception e) {
        	log.error("", e);
        } 
    }

    
    // ========== private method ===============
    
    private MemcachedClient buildClient(String url) throws IOException {
        MemcachedClientBuilder builder = new   XMemcachedClientBuilder(AddrUtil.getAddresses(url));
        MemcachedClient memcachedClient=builder.build();
        return memcachedClient;
    }
}

