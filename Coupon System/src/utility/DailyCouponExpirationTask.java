package utility;

import dbdao.CouponDBDAO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import beans.Coupon;

public class DailyCouponExpirationTask extends TimerTask {
	Timer timer = new Timer();
	long delay = 1000L;
    long period = 1000L * 60L * 60L * 24L;
	
	private static TimerTask timerTask = new TimerTask() {
		
		@Override
		public void run(){
			Collection<Coupon> removeList = null;
			CouponDBDAO couponRemover = new CouponDBDAO();
			try {
				removeList = (Collection<Coupon>) couponRemover.getAllCoupons();
			} catch (Exception e) {
				try {
					throw new Exception("(Expired coupon removal) couldn't get coupons.");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			Iterator<Coupon> iter = removeList.iterator();
			if (iter != null) {
				while (iter.hasNext()) {
					Coupon thisCoupon = iter.next();
					if (thisCoupon.getEndDate().isBefore(LocalDate.now())
							&& thisCoupon.getExpirationStatus().equals("ACTIVE")) {
						try {
							couponRemover.removeCoupon(thisCoupon);
						} catch (Exception e) {
							try {
								throw new Exception("Couldn't remove outdated coupons.");
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}

					}
				}
			}
		}
	};
	
	public static void stopTask() {
		timerTask.cancel();
	}

	@Override
	public void run() {		
		timer.schedule(timerTask, delay, period);
		System.out.println("Started checking or expired coupons.");
	}
}
