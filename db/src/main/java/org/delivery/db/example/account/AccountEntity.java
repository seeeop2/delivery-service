package org.delivery.db.example.account;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "account")
@Entity
public class AccountEntity extends BaseEntity {
}
