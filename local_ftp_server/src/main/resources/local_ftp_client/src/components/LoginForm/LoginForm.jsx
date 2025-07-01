import styles from "./LoginForm.module.css";
import { useData } from "../../scripts/useData";
import Button from "../Button/Button";

function LoginForm() {
  const [data, setData] = useData();

  const element = (
    <div className={styles.container}>
      <h2 className={styles.title}>Login to local-ftp</h2>
      <div className={styles.form}>
        <label htmlFor="nickname" className={styles.label}>
          Nickname
        </label>
        <input
          id="nickname"
          className={styles.field}
          type="text"
          placeholder={data.settings.nickname}
        />
        <label htmlFor="password" className={styles.label}>
          Password
        </label>
        <input id="password" className={styles.field} type="password" />
        <div className={styles.btnContainer}>
          <Button text="Login" border="rounded" iconName="arrow_forward" />
        </div>
      </div>
      {/* <Button onClick={fixLightMode} text="Fix light mode" /> */}
    </div>
    
  );

  function fixLightMode() {
    const newData = { ...data };
    newData.settings.darkMode = "light";
    setData(newData);
  }

  return element;
}

export default LoginForm;
