package com.tatechsoft.project.gensource;

import com.google.common.base.CaseFormat;
import com.tatechsoft.project.common.utils.AppUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

class GenSource {

    public static void main(String[] args) throws IOException {

        Scanner scannerEntity = new Scanner(System.in);  // Create a Scanner object
        System.out.print("Enter Entity name: ");

        String entityName = scannerEntity.nextLine();  // Read user input
        System.out.println("=============================");
        System.out.println("Entity name is: " + entityName);  // Output user input

        String releaseFolder = "./src/main/java/com/ooo/project/gensource/result/";
        String dir = releaseFolder + entityName;
        String dirFrontend = releaseFolder;

        //  Check folder exit
        File convert = new File(releaseFolder);
        if (!convert.exists()) convert.mkdirs();

        //  ==> Entity
        File file = new File(dir + ".java");

        FileWriter writer = new FileWriter(file);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import com.ooo.project.common.base.entity.BaseEntity;\n" +
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
                "@AttributeOverride(name = \"ID\", column = @Column(name = \"ID\", nullable = false))\n" +
                "public class " + entityName + " extends BaseEntity {\n\n" +
                "   //@Column(name = \"COLUMN_NAME\", length = 200, nullable = false)\n" +
                "   //private String columnName;\n\n" +
                "   //@OneToMany(fetch = FetchType.EAGER, mappedBy = \"" + AppUtils.entityLowerCase(entityName) + "\")  <-- use Camel Case Ex. EntityName ==> entityName \n" +
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
                "import com.ooo.project.common.base.repository.CommonJpaCrudRepository;\n" +
                "import com.ooo.project.gensource.result." + entityName + ";\n\n" +
                "public interface " + entityName + "Repo extends CommonJpaCrudRepository<" + entityName + ", Long>, " + entityName + "RepoCustom {\n\n}");
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
                "            jpql.append(\" ORDER BY " + subEntityName.toLowerCase(Locale.ROOT) + ".createdDate DESC\");\n" +
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
                "\tprivate " + entityName + "Repo " + AppUtils.entityLowerCase(entityName) + "Repo;\n\n" +
                "    @Transactional\n" +
                "    public void delete(Long id) {\n" +
                "        " + AppUtils.entityLowerCase(entityName) + "Repo.deleteById(id);\n" +
                "//            " + AppUtils.entityLowerCase(entityName) + "Repo.deleteSoft(id);\n" +
                "//            " + AppUtils.entityLowerCase(entityName) + "Repo.inActive(id);\n" +
                "        logger().info(\"Method delete {} success\", id);\n\n" +
                "    }\n" +
                "}");
        writer.close();
        System.out.println("File Service is created!");

        //  ==> Controller
        file = new File(dir + "Controller.java");

        writer = new FileWriter(file);
        writer.write("package com.ooo.project.gensource.result;\n\n" +
                "import com.ooo.project.common.base.controller.BaseController;\n" +
                "import com.ooo.project.common.base.exception.DataNotFoundException;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.http.ResponseEntity;\n" +
                "import org.springframework.web.bind.annotation.*;\n" +
                "import javax.servlet.http.HttpServletRequest;\n\n" +
                "import java.util.Optional;\n\n" +
                "@RestController\n" +
                "@RequestMapping(\"/api/" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityName) + "\")\n" +
                "public class " + entityName + "Controller extends BaseController {\n" +
                "\n\t@Autowired\n" +
                "\tprivate " + entityName + "Repo " + AppUtils.entityLowerCase(entityName) + "Repo;" +
                "\n\n\t@Autowired\n" +
                "    private " + entityName + "Service " + AppUtils.entityLowerCase(entityName) + "Service;" +
                "\n\n\t@PostMapping(\"/search\")\n" +
                "    public ResponseEntity<?> search" + entityName + "(@RequestBody " + entityName + "Criteria criteria, HttpServletRequest http) {\n" +
                "        isPermission(\"PRIVILEGE_XXX\", http);\n" +
                "        return ResponseEntity.ok(" + AppUtils.entityLowerCase(entityName) + "Repo.search" + entityName + "(criteria));\n" +
                "    }" +
                "\n\n\t@GetMapping(\"/get-by-id/{id}\")\n" +
                "    public ResponseEntity<?> get" + entityName + "ById(@PathVariable long id, HttpServletRequest http) {\n" +
                "        isPermission(\"PRIVILEGE_XXX\", http);\n" +
                "        Optional<" + entityName + "> resultOptional = " + AppUtils.entityLowerCase(entityName) + "Repo.findById(id);\n" +
                "        if (resultOptional.isEmpty()) throw new DataNotFoundException(String.format(\"Data not found id: %s\", id));\n" +
                "        return ResponseEntity.ok(resultOptional.get());\n" +
                "    }" +
                "\n\n\t@PostMapping(\"/save\")\n" +
                "    \tpublic ResponseEntity<?> save" + entityName + "(@RequestBody " + entityName + " " + AppUtils.entityLowerCase(entityName) + ", HttpServletRequest http) {\n" +
                "        isPermission(\"PRIVILEGE_XXX\", http);\n" +
                "        " + AppUtils.entityLowerCase(entityName) + " = " + AppUtils.entityLowerCase(entityName) + "Repo.save(" + AppUtils.entityLowerCase(entityName) + ");\n" +
                "        logger().info(\"Method save " + entityName + " success\");\n" +
                "        return ResponseEntity.ok(" + AppUtils.entityLowerCase(entityName) + ");\n" +
                "    }" +
                "\n\n\t@DeleteMapping(\"/delete-by-id/{id}\")\n" +
                "    public ResponseEntity<?> delete" + entityName + "(@PathVariable long id, HttpServletRequest http) {\n" +
                "        isPermission(\"PRIVILEGE_XXX\", http);\n" +
                "        " + AppUtils.entityLowerCase(entityName) + "Service.delete(id);\n" +
                "        logger().info(\"Method delete" + entityName + " {} success\", id);\n" +
                "        return ResponseEntity.ok(message(\"SYS_D1\"));\n" +
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

        //  ==> Frontend List Page
        file = new File(dirFrontend + "index.vue");

        writer = new FileWriter(file);
        writer.write("<template>\n" +
                "  <div>\n" +
                "    <Breadcrumb :menuList=\"['xxxx']\" />\n" +
                "    <b-card class=\"mb-4 shadow-sm\">\n" +
                "      <b-row class=\"mb-2\">\n" +
                "        <b-col cols=\"12\" sm=\"6\" md=\"6\" lg=\"6\">\n" +
                "          <TextInput title=\"input1\" />\n" +
                "        </b-col>\n" +
                "        <b-col cols=\"12\" sm=\"6\" md=\"6\" lg=\"6\">\n" +
                "          <TextInput title=\"input2\" maxlength=\"10\" />\n" +
                "        </b-col>\n" +
                "      </b-row>\n" +
                "      <b-row class=\"mb-5\">\n" +
                "        <b-col class=\"text-center\">\n" +
                "          <p style=\"margin-top: 38px\"></p>\n" +
                "          <ButtonSearch @click=\"search()\" />\n" +
                "          <ButtonReset @click=\"reset()\" />\n" +
                "        </b-col>\n" +
                "      </b-row>\n" +
                "      <b-row class=\"mb-3\">\n" +
                "        <b-col>\n" +
                "          <font-awesome-icon icon=\"fa-solid fa-users\" />\n" +
                "          ทั้งหมด {{ dataTable?.total || 0 }} รายการ\n" +
                "        </b-col>\n" +
                "        <b-col class=\"text-right\">\n" +
                "          <ButtonIconAdd @click=\"goCreate()\" />\n" +
                "        </b-col>\n" +
                "      </b-row>\n" +
                "      <Table\n" +
                "        :dataTable=\"dataTable\"\n" +
                "        :formSearch=\"formSearch\"\n" +
                "        @change=\"search\"\n" +
                "        :loading=\"loading\"\n" +
                "      >\n" +
                "        <template v-slot:header>\n" +
                "          <th>#</th>\n" +
                "          <th>column 1</th>\n" +
                "          <th>column 2<</th>\n" +
                "          <th class=\"text-center\">แก้ไขล่าสุดโดย</th>\n" +
                "          <th class=\"text-center\">วันที่แก้ไขล่าสุด</th>\n" +
                "          <th></th>\n" +
                "        </template>\n" +
                "        <template v-slot:body>\n" +
                "          <tr v-if=\"dataTable && dataTable?.total == 0\">\n" +
                "            <td class=\"text-center\" colspan=\"7\">ไม่พบข้อมูล</td>\n" +
                "          </tr>\n" +
                "          <tr v-for=\"(item, index) in dataTable?.data\" :key=\"index\">\n" +
                "            <td>\n" +
                "              {{ (dataTable?.page - 1) * dataTable?.pageSize + (index + 1) }}\n" +
                "            </td>\n" +
                "            <td></td>\n" +
                "            <td></td>\n" +
                "            <td class=\"text-center\">{{ item.updatedBy }}</td>\n" +
                "            <td class=\"text-center\">{{ item.updatedDateStr }}</td>\n" +
                "            <td class=\"text-right\">\n" +
                "              <ButtonView @click=\"onView(item.id)\" />\n" +
                "              <!-- v-if=\"isPermission(['CAN_GET_XXX'])\" -->\n" +
                "              <ButtonIconDelete @click=\"deleteById(item.id)\" />\n" +
                "              <!-- v-if=\"isPermission(['CAN_DELETE_XXX'])\" -->\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </template>\n" +
                "      </Table>\n" +
                "    </b-card>\n" +
                "  </div>\n" +
                "</template>\n" +
                "<script>\n" +
                "// import " + entityName + "Service from \"~/service/" + entityName + "Service.js\";\n" +
                "import AuthUtils from \"~/common/utils/AuthUtils\";\n" +
                "const FORM_PATH = \"/xxxx/xxxx\";\n" +
                "export default {\n" +
                "  layout: \"admin\",\n" +
                "\n" +
                "  data() {\n" +
                "    return {\n" +
                "      loading: false,\n" +
                "      formSearch: {\n" +
                "        page: 1,\n" +
                "        pageSize: 25,\n" +
                "      },\n" +
                "      dataTable: {\n" +
                "        data: [],\n" +
                "        total: 100,\n" +
                "      },\n" +
                "    };\n" +
                "  },\n" +
                "\n" +
                "  mounted() {\n" +
                "    this.search();\n" +
                "  },\n" +
                "\n" +
                "  methods: {\n" +
                "    isPermission(privilegeCodeList) {\n" +
                "      const userProfile = this.$store.state.auth.user;\n" +
                "      return AuthUtils.isPermission(userProfile, privilegeCodeList);\n" +
                "    },\n" +
                "    async search() {\n" +
                "      this.loading = true;\n" +
                "      try {\n" +
                "        this.dataTable = await " + entityName + "Service.search(this.formSearch);  \n" +
                "      } finally{ \n" +
                "        this.loading = false;\n" +
                "      }    \n" +
                "    },\n" +
                "    goCreate() {\n" +
                "      this.$router.push(FORM_PATH);\n" +
                "    },\n" +
                "    onView(id) {\n" +
                "      this.$router.push({ path: FORM_PATH, query: { id: id } });\n" +
                "    },\n" +
                "    onEdit(id) {\n" +
                "      this.$router.push({ path: FORM_PATH, query: { id: id } });\n" +
                "    },\n" +
                "    onDelete(id) {\n" +
                "      this.$bus.emit(\"confirm-modal\", {\n" +
                "        cb: () => {\n" +
                "          this.deleteById(id);\n" +
                "        },\n" +
                "      });\n" +
                "    },\n" +
                "    async deleteById(id) {\n" +
                "      try {\n" +
                "        // await " + entityName + "Service.deleteById(id);\n" +
                "        // this.$notify(AppConstant.NOTI_SUCCESS);\n" +
                "        // if (this.formSearch.page != 1 && this.dataTable.data.length == 1) {\n" +
                "        //   this.formSearch.page = this.formSearch.page - 1;\n" +
                "        // }\n" +
                "        this.search();\n" +
                "      } catch (err) {\n" +
                "        console.error(err);\n" +
                "      }\n" +
                "    },\n" +
                "    reset() {\n" +
                "      this.formSearch = {\n" +
                "        page: 1,\n" +
                "        pageSize: 25,\n" +
                "      };\n" +
                "      this.search();\n" +
                "    },\n" +
                "  },\n" +
                "};\n" +
                "</script>\n");
        writer.close();
        System.out.println("File Frontend List Page");

        file = new File(dirFrontend + "form.vue");

        writer = new FileWriter(file);
        writer.write("<template>\n" +
                "  <div>\n" +
                "    <Breadcrumb :menuList=\"['xxxx', 'xxxx']\" />\n" +
                "    <b-card class=\"shadow-sm\">\n" +
                "      <form @submit.prevent=\"submit\" class=\"regiser-form\">\n" +
                "        <b-row class=\"mb-3 mt-3\">\n" +
                "          <b-col>\n" +
                "            <h5>Header</h5>\n" +
                "          </b-col>\n" +
                "          <b-col class=\"text-right\">\n" +
                "            <ButtonBack />\n" +
                "          </b-col>\n" +
                "        </b-row>\n" +
                "\n" +
                "        <b-row>\n" +
                "          <b-col>\n" +
                "            <TextInput title=\"input1\" v-model=\"formData.input1\" :required=\"true\" :readonly=\"readMode\" />\n" +
                "          </b-col>\n" +
                "          <b-col>\n" +
                "            <TextInput title=\"input2\" v-model=\"formData.input2\"  :readonly=\"readMode\" />\n" +
                "          </b-col>\n" +
                "          <b-col>\n" +
                "            <DateInput title=\"date\" v-model=\"formData.date\"  :readonly=\"readMode\" />\n" +
                "          </b-col>\n" +
                "          <b-col>\n" +
                "            <TextInput\n" +
                "              title=\"input number\"\n" +
                "              v-model=\"formData.number\"\n" +
                "              :readonly=\"readMode\"\n" +
                "              :number=\"true\"\n" +
                "            />\n" +
                "          </b-col>\n" +
                "        </b-row>\n" +
                "        <b-row class=\"mt-4\" v-if=\"$route.query.id\">\n" +
                "          <b-col lg=\"3\">\n" +
                "            <TextInput\n" +
                "              title=\"สร้างโดย\"\n" +
                "              v-model=\"formData.createdBy\"\n" +
                "              :readonly=\"true\"\n" +
                "            />\n" +
                "            <TextInput\n" +
                "              title=\"วันที่สร้าง\"\n" +
                "              v-model=\"formData.createdDateStr\"\n" +
                "              :readonly=\"true\"\n" +
                "            />\n" +
                "          </b-col>\n" +
                "          <b-col lg=\"3\"></b-col>\n" +
                "          <b-col lg=\"3\"></b-col>\n" +
                "          <b-col lg=\"3\">\n" +
                "            <TextInput\n" +
                "              title=\"แก้ไขล่าสุดโดย\"\n" +
                "              v-model=\"formData.updatedBy\"\n" +
                "              :readonly=\"true\"\n" +
                "            />\n" +
                "            <TextInput\n" +
                "              title=\"วันที่แก้ไขล่าสุด\"\n" +
                "              v-model=\"formData.updatedDateStr\"\n" +
                "              :readonly=\"true\"\n" +
                "            />\n" +
                "          </b-col>\n" +
                "        </b-row>\n" +
                "        <br />\n" +
                "        <b-row class=\"mt-5\">\n" +
                "          <b-col class=\"text-center\">\n" +
                "            <ButtonSave type=\"submit\" v-if=\"!readMode\" />\n" +
                "            <!-- isPermission(['CAN_CREATE_XXX', 'CAN_EDIT_XXX']) -->\n" +
                "            <ButtonEdit @click=\"onEditMode()\" v-if=\"readMode && canEdit()\" />\n" +
                "          </b-col>\n" +
                "        </b-row>\n" +
                "      </form>\n" +
                "    </b-card>\n" +
                "  </div>\n" +
                "</template>\n" +
                "<script>\n" +
                "// import " + entityName + "Service from \"~/service/" + entityName + "Service\";\n" +
                "import AppConstant from \"~/common/constants/AppConstant\";\n" +
                "import AuthUtils from \"~/common/utils/AuthUtils\";\n" +
                "\n" +
                "export default {\n" +
                "  layout: \"admin\",\n" +
                "  data() {\n" +
                "    return {\n" +
                "      readMode: false,\n" +
                "      formData: {\n" +
                "        input1: \"\",\n" +
                "        input2: \"\",\n" +
                "        date: \"2024-01-04\",\n" +
                "        number: '200'\n" +
                "      },\n" +
                "    };\n" +
                "  },\n" +
                "\n" +
                "  async mounted() {\n" +
                "    if (this.$route.query.id != undefined) {\n" +
                "      this.readMode = true;\n" +
                "      //   await this.getById(this.$route.query.id);\n" +
                "    }\n" +
                "  },\n" +
                "\n" +
                "  methods: {\n" +
                "    isPermission(privilegeCodeList) {\n" +
                "      const userProfile = this.$store.state.auth.user;\n" +
                "      return AuthUtils.isPermission(userProfile, privilegeCodeList);\n" +
                "    },\n" +
                "    canEdit() {\n" +
                "      const isPermission = this.isPermission([\"CAN_EDIT_PROFILE\"]);\n" +
                "      return isPermission;\n" +
                "    },\n" +
                "    onEditMode() {\n" +
                "      this.readMode = false;\n" +
                "    },\n" +
                "    async getById(id) {\n" +
                "      this.formData = await " + entityName + "Service.getById(id);\n" +
                "    },\n" +
                "    async submit() {\n" +
                "      try {\n" +
                "        await this.$bus.emit(\"confirm-modal\", {\n" +
                "          cb: async () => {\n" +
                "            // await " + entityName + "Service.save(this.formData);\n" +
                "            this.$notify(AppConstant.NOTI_SUCCESS);\n" +
                "            this.readMode = true;\n" +
                "          },\n" +
                "        });\n" +
                "      } catch (err) {\n" +
                "        console.error(\"err: \", err);\n" +
                "      }\n" +
                "    },\n" +
                "  },\n" +
                "};\n" +
                "</script>\n");
        writer.close();
        System.out.println("File Frontend Service for call API");

        file = new File(dir + "Service.js");

        writer = new FileWriter(file);
        writer.write("import AxiosService from \"@/common/service/axios-service\";\n" +
                "const axios = new AxiosService();\n" +
                "const DEFAULT_PATCH = \"/api/" + CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, entityName) + "\";\n" +
                "export default {\n" +
                "  DEFAULT_PATCH,\n" +
                "  search(data) {\n" +
                "    return axios.doPost(`${DEFAULT_PATCH}/search`, data);\n" +
                "  },\n" +
                "  getById(id) {\n" +
                "    return axios.doGet(`${DEFAULT_PATCH}/get-by-id/${id}`);\n" +
                "  },\n" +
                "  updateProfile(data) {\n" +
                "    return axios.doPost(`${DEFAULT_PATCH}/update`, data);\n" +
                "  },\n" +
                "  deleteById(id) {\n" +
                "    return axios.doDelete(`${DEFAULT_PATCH}/delete-by-id/${id}`, data);\n" +
                "  },\n" +
                "};\n");
        writer.close();
        System.out.println("File Frontend Service for call API");

        System.out.println("Total file generation is 10");
        System.out.println("=============================");
    }
}
