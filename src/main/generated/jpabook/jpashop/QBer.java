package jpabook.jpashop;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBer is a Querydsl query type for Ber
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBer extends EntityPathBase<Ber> {

    private static final long serialVersionUID = -1431613272L;

    public static final QBer ber = new QBer("ber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath username = createString("username");

    public QBer(String variable) {
        super(Ber.class, forVariable(variable));
    }

    public QBer(Path<? extends Ber> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBer(PathMetadata metadata) {
        super(Ber.class, metadata);
    }

}

