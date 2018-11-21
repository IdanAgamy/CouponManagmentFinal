import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.idan.coupons.beans.Coupon;
import com.idan.coupons.dao.CouponDao;
import com.idan.coupons.enums.CouponType;
import com.idan.coupons.exceptions.ApplicationException;

public class DaoTester {
	
	@Test
	public void testGetCouponByCouponId() throws ApplicationException {
		CouponDao cd = new CouponDao();
		Coupon resultCoupon = cd.getCouponByCouponId(1L);
		Coupon expectedCoupon = new Coupon(1l, "best song f all time", "1987-10-10", "3000-10-10", 2, CouponType.Food, "Never gonna give you up", 67.8, "https://www.youtube.com/watch?v=dQw4w9WgXcQ", 1);
		assertEquals(expectedCoupon, resultCoupon);
	}

	/*
	 * createCouponTest(CouponDao)
getCouponByCouponIdTest(CouponDao)
getAllCouponsTest(CouponDao)
removeCouponByIDTest(CouponDao)
removeBoughtCouponByIDTest(CouponDao)
removeCustomerPurchasesByCustomerIDTest(CouponDao)
updateCouponTest(CouponDao)
getCouponByTypeTest(CouponDao)
getCouponInOrderByPriceTest(CouponDao)
getCouponsUpToPriceTest(CouponDao)
getCouponsUpToEndDateTest(CouponDao)
getCouponsByCompanyIDTest(CouponDao)
getCouponsByCustomerIDTest(CouponDao)
buyCouponTest(CouponDao)
isCouponExistByTitleTest(CouponDao)
isCouponTitleExistForUpdateTest(CouponDao)
isCouponAlreadyPurchasedByCustomerIDTest(CouponDao)
createCompanyTest(CompanyDao)
removeCompanyByCompanyIDTest(CompanyDao)
updateCompanyTest(CompanyDao)
getCompanyByComapnyIdTest(CompanyDao)
getAllCompaniesTest(CompanyDao)
loginTest(CompanyDao)
isCompanyExistByNameTest(CompanyDao)
isCompanyNameUpdateAvailableTest(CompanyDao)
isCompanyExistByEmailTest(CompanyDao)
isCompanyEmailUpdateAvailableTest(CompanyDao)
createCustomerTest(CustomerDao)
removeCustomerByCustomerIDTest(CustomerDao)
updateCustomerTest(CustomerDao)
getCustomerByCustomerIdTest(CustomerDao)
getAllCustomersTest(CustomerDao)
loginTest(CustomerDao)
isCustomerExistByEmailTest(CustomerDao)
isCustomerEmailUpdateAvailableTest(CustomerDao)

	 */

}
