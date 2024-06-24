package com.tatechsoft.project.gensource;

import com.google.common.base.CaseFormat;
import com.tatechsoft.project.common.utils.AppUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

class GenSourceUUID {

    public static void main(String[] args) throws IOException {

        Scanner scannerEntity = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Enter Entity name: ");

        String entityName = scannerEntity.nextLine();  // Read user input
        System.out.println("=============================");
        System.out.println("Entity name is: " + entityName);  // Output user input

        String releaseFolder = "./src/main/java/com/ooo/project/common/gensource/result/";
        String dir = releaseFolder + entityName;

        //  Check folder exit
        File convert = new File(releaseFolder);
        if (!convert.exists()) convert.mkdirs();

        //  ==> Entity
        File file = new File(dir + ".java");

        FileWriter writer = new FileWriter(file);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import com.ooo.project.common.base.entity.UUIDBaseEntity;\n" +
                "import lombok.Getter;\n" +
                "import lombok.Setter;\n" +
                "import javax.persistence.AttributeOverride;\n" +
                "import javax.persistence.Column;\n" +
                "import javax.persistence.Entity;\n" +
                "import javax.persistence.Table;\n\n" +
                "@Entity\n" +
                "@Table(name = \"T_" + CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entityName) + "\")\n" +
                "@Getter\n" +
                "@Setter\n" +
                "@AttributeOverride(name = \"id\", column = @Column(name = \"" + CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, entityName) + "_ID\", nullable = false))\n" +
                "public class " + entityName + " extends UUIDBaseEntity {\n\n" +
                "   //@Column(length = 200, nullable = false)\n" +
                "   //private String columnName;\n\n" +
                "   //@OneToMany(fetch = FetchType.EAGER, mappedBy = \"" + AppUtils.entityLowerCase(entityName) + "\")  <-- use Camel Case Ex. EntityName ==> entityName\n" +
                "   //@JoinColumn(name = \"ENTITY_ID\")\n" +
                "   //private List<Entity> EntityList;\n\n" +
                "   //@ManyToOne(fetch = FetchType.EAGER)\n" +
                "   //@JoinColumn(name = \"ENTITY_ID\")\n" +
                "   //@OnDelete(action = OnDeleteAction.CASCADE)\n" +
                "   //@JsonIgnore\n" +
                "   //private Entity entity;" +
                "\n}");
        writer.close();
        System.out.println("File Entity is created!");

        //  ==> Repository
        file = new File(dir + "Repo.java");

        writer = new FileWriter(file);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import com.ooo.project.common.base.repository.CommonJpaUUIDCrudRepository;\n" +
                "import java.util.UUID;\n" +
                "import com.ooo.project.gensource.result." + entityName + ";\n\n" +
                "public interface " + entityName + "Repo extends CommonJpaUUIDCrudRepository<" + entityName + ", UUID>, " + entityName + "RepoCustom {\n\n}");
        writer.close();
        //Create the file
        if (file.createNewFile()) {
            System.out.println("File Repository is created!");
        } else {
            System.out.println("File already Repository exists.");
        }
        //  ==> RepositoryCustom
        file = new File(dir + "RepoCustom.java");

        writer = new FileWriter(file);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import com.ooo.project.common.bean.DataTableBean;\n" +
                "import com.ooo.project.gensource.result." + entityName + "Criteria;\n" +
                "import com.ooo.project.gensource.result." + entityName + ";\n\n" +
                "public interface " + entityName + "RepoCustom {\n" +
                "    DataTableBean<" + entityName + "> search" + entityName + "(" + entityName + "Criteria criteria);\n" +
                "}");
        writer.close();
        System.out.println("File RepositoryCustom is created!");

        //  ==> RepositoryImpl
        file = new File(dir + "RepoImpl.java");

        writer = new FileWriter(file);
        String subEntityName = entityName.substring(0, 1);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import com.ooo.project.common.bean.DataTableBean;\n" +
                "import com.ooo.project.gensource.result." + entityName + "Criteria;\n" +
                "import com.ooo.project.gensource.result." + entityName + ";\n" +
                "import com.ooo.project.common.util.AppUtils;\n" +
                "import com.ooo.project.common.util.CriteriaQueryUtils;\n" +
                "import javax.persistence.EntityManager;\n" +
                "import javax.persistence.PersistenceContext;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n\n" +
                "public class " + entityName + "RepoImpl implements " + entityName + "RepoCustom {\n" +
                "\n" +
                "    @PersistenceContext\n" +
                "    EntityManager em;\n\n" +
                "\tpublic DataTableBean<" + entityName + "> search" + entityName + "(" + entityName + "Criteria criteria) {\n" +
                "        Map<String, Object> params = new HashMap<>();\n" +
                "        StringBuilder jpql = new StringBuilder(\"SELECT " + subEntityName.toLowerCase(Locale.ROOT) + " FROM " + entityName + " " + subEntityName.toLowerCase(Locale.ROOT) + " WHERE 1=1 \");\n\n" +
                "//        if (AppUtils.hasValue(criteria.getParam1())) {\n" +
                "//            jpql.append(\" AND " + subEntityName.toLowerCase(Locale.ROOT) + ".param1 = :param1\");\n" +
                "//            params.put(\"param1\", criteria.getParam1());\n" +
                "//        }\n\n" +
                "//        jpql.append(\" AND " + subEntityName.toLowerCase(Locale.ROOT) + ".isDeleted = :isDeleted\");\n" +
                "//        params.put(\"isDeleted\", \"N\");\n\n" +
                "        //  Order By\n" +
                "        if (AppUtils.hasValue(criteria.getSort())) {\n" +
                "            jpql.append(\" ORDER BY " + subEntityName.toLowerCase(Locale.ROOT) + ".\" + criteria.getSort() + \" \" + criteria.getSortDirection());\n" +
                "        }else{\n" +
                "            jpql.append(\" ORDER BY " + subEntityName.toLowerCase(Locale.ROOT) + ".createdDate desc\");\n" +
                "        }\n\n" +
                "        return new CriteriaQueryUtils().createQuery(em, jpql, criteria, params);\n" +
                "    }" +
                "\n}");
        writer.close();
        System.out.println("File RepositoryImpl is created!");

        //  ==> Service
        file = new File(dir + "Service.java");

        writer = new FileWriter(file);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import com.ooo.project.common.base.service.BaseService;\n" +
                "import org.springframework.stereotype.Service;\n\n" +
                "import javax.transaction.Transactional;\n\n" +
                "@Service\n" +
                "public class " + entityName + "Service extends BaseService {\n\n" +
                "\t@Autowired\n" +
                "\tprivate " + entityName + "Repo " + AppUtils.entityLowerCase(entityName) + "Repo;\n}");
        writer.close();
        System.out.println("File Service is created!");

        //  ==> Controller
        file = new File(dir + "Controller.java");

        writer = new FileWriter(file);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import com.ooo.project.common.base.controller.BaseController;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import com.ooo.project.common.bean.MessageBean;\n" +
                "import org.springframework.http.HttpStatus;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import java.util.UUID;\n" +
                "import com.ooo.project.common.base.exception.PermissionException;\n" +
                "import org.springframework.web.bind.annotation.*;\n\n" +
                "import java.util.Optional;\n\n" +
                "@RestController\n" +
                "@RequestMapping(\"/api/" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityName) + "\")\n" +
                "public class " + entityName + "Controller extends BaseController {\n" +
                "\n\t@Autowired\n" +
                "\tprivate " + entityName + "Repo " + AppUtils.entityLowerCase(entityName) + "Repo;" +
                "\n\n\t@PostMapping(\"/search\")\n" +
                "    public ResponseEntity<?> search" + entityName + "(@RequestBody " + entityName + "Criteria criteria) {\n" +
                "        try {\n" +
                "            isPermission(\"PRIVILEGE_XXX\");\n" +
                "            return ResponseEntity.ok(" + AppUtils.entityLowerCase(entityName) + "Repo.search" + entityName + "(criteria));\n" +
                "        } catch (PermissionException e) {\n" +
                "            return permissionException(e);\n" +
                "        } catch (Exception e) {\n" +
                "            return processException(e);\n" +
                "        }\n" +
                "    }" +
                "\n\n\t@GetMapping(\"/{id}\")\n" +
                "    public ResponseEntity<?> get" + entityName + "ById(@PathVariable UUID id) {\n" +
                "        try {\n" +
                "            isPermission(\"PRIVILEGE_XXX\");\n" +
                "            Optional<" + entityName + "> resultOptional = " + AppUtils.entityLowerCase(entityName) + "Repo.findById(id);\n" +
                "            if (resultOptional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageBean(\"Not found \" + id));\n" +
                "            return ResponseEntity.ok(resultOptional.get());\n" +
                "        } catch (PermissionException e) {\n" +
                "            return permissionException(e);\n" +
                "        } catch (Exception e) {\n" +
                "            return processException(e);\n" +
                "        }\n" +
                "    }" +
                "\n\n\t@PostMapping(\"/\")\n" +
                "    \tpublic ResponseEntity<?> save" + entityName + "(@RequestBody " + entityName + " " + AppUtils.entityLowerCase(entityName) + ") {\n" +
                "        try {\n" +
                "            isPermission(\"PRIVILEGE_XXX\");\n" +
                "            " + AppUtils.entityLowerCase(entityName) + " = " + AppUtils.entityLowerCase(entityName) + "Repo.save(" + AppUtils.entityLowerCase(entityName) + ");\n" +
                "            getLogger().info(\"Method save " + entityName + " success\");\n" +
                "            return ResponseEntity.ok(" + AppUtils.entityLowerCase(entityName) + ");\n" +
                "        } catch (PermissionException e) {\n" +
                "            return permissionException(e);\n" +
                "        } catch (Exception e) {\n" +
                "            return processException(e);\n" +
                "        }\n" +
                "    }" +
                "\n\n\t@DeleteMapping(\"/{id}\")\n" +
                "    public ResponseEntity<?> delete" + entityName + "(@PathVariable UUID id) {\n" +
                "        try {\n" +
                "            isPermission(\"PRIVILEGE_XXX\");\n" +
                "            " + AppUtils.entityLowerCase(entityName) + "Repo.deleteById(id);\n" +
                "//            " + AppUtils.entityLowerCase(entityName) + "Repo.deleteSoft(id);\n " +
                "//            " + AppUtils.entityLowerCase(entityName) + "Repo.inActive(id);\n" +
                "            getLogger().info(\"Method delete" + entityName + " {} success\", id);\n" +
                "            return ResponseEntity.ok(message(\"SYS_D1\"));\n" +
                "        } catch (PermissionException e) {\n" +
                "            return permissionException(e);\n" +
                "        } catch (Exception e) {\n" +
                "            return processException(e);\n" +
                "        }\n" +
                "    }" +
                "\n}");
        writer.close();
        System.out.println("File Controller is created!");

        //  ==> Criteria
        file = new File(dir + "Criteria.java");

        writer = new FileWriter(file);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import com.ooo.project.common.base.criteria.BaseCriteria;\n" +
                "import lombok.Getter;\n" +
                "import lombok.Setter;\n\n" +
                "@Getter\n" +
                "@Setter\n" +
                "public class " + entityName + "Criteria extends BaseCriteria {\n\n}");
        writer.close();
        System.out.println("File Criteria is created!");
        System.out.println("Total file generation is 7");
        System.out.println("=============================");
    }
}
