package org.robert.tips.stryktips.finder.desktopclient.domain.infrastructure.bd;

/**
 * Factory class for creating business delegates. Pattern: Factory Method
 * [GoF95].
 * 
 * @author Robert G.
 * 
 */
public class BusinessDelegateFactory {

	/**
	 * Create a Stryktips Business delegate.
	 * 
	 * @param type
	 *            of delegate to create.
	 * @return new BD.
	 */
	public StryktipsFinderBD createStryktipsFinderBD(
			BusinessDelegateFactoryType type) {
		if (BusinessDelegateFactoryType.MOCK == type) {
			StryktipsFinderBD mock = new StryktipsFinderBDMock();

			return mock;
		} else if (BusinessDelegateFactoryType.RELEASE == type) {
			StryktipsFinderBD release = new StryktipsFinderBDImpl();

			return release;
		} else {
			throw new RuntimeException(
					"Invalid key given to Business Delegate factory, key = "
							+ type);
		}
	}

	/**
	 * Get delegate setup/ used by current runtime.
	 */
	public StryktipsFinderBD getRuntimeBD() {
		StryktipsFinderBD stryktipsFinderBD = createStryktipsFinderBD(BusinessDelegateFactoryType.RELEASE);

		return stryktipsFinderBD;
	}
}