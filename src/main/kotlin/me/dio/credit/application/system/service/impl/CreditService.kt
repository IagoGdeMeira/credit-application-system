package me.dio.credit.application.system.service.impl

import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.service.ICreditService
import java.util.UUID

class CreditService(
    private val creditRepository: CreditRepository,
    private val customerService: CustomerService
): ICreditService {
    override fun save(credit: Credit): Credit {
        credit.apply { customer = customerService.findById(credit.customer?.id!!) }
        return this.creditRepository.save(credit)
    }

    override fun findAllByCustomer(customerId: Long): List<Credit> = creditRepository.findAllByCredits(customerId)

    override fun findByCreditCode(customerId: Long, creditCode: UUID): Credit {
        val credit: Credit = this.creditRepository.findByCreditCode(creditCode)
            ?: throw RuntimeException("Credit code $creditCode not found")
        return if(credit.customer?.id == customerId) credit else throw RuntimeException("Contact admin")
    }
}